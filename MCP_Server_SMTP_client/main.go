package main

import (
	"context"
	"crypto/sha512"
	"encoding/base64"
	"fmt"
	"github.com/mark3labs/mcp-go/mcp"
	"github.com/mark3labs/mcp-go/server"
	"gopkg.in/mail.v2" // Import gomail
	"log"
	"os"
	"strings"
)

//TIP <p>To run your code, right-click the code and select <b>Run</b>.</p> <p>Alternatively, click
// the <icon src="AllIcons.Actions.Execute"/> icon in the gutter and select the <b>Run</b> menu item from here.</p>

func printDebugMessage(logger *log.Logger, debugMode bool, message ...string) {
	if debugMode {
		logger.Println(message)
	}
}
func main() {

	logger := log.New(os.Stderr, "MCP_SERVER_SMTP: ", log.Ldate|log.Ltime|log.Lshortfile)
	//used env-var instead of cli switch since my anti-virus was marking the executable as a threat once I include flag.parse
	debugMode := false
	debugFlag, found := os.LookupEnv("debug_mode")
	if found {
		debugMode = debugFlag == "true"
	}
	logger.Println("debugMode:", debugMode)

	smtpUser, found := os.LookupEnv("smtp_user")
	if !found {
		logger.Fatal("smtp_user environment variable not found")
		//return fmt.Errorf("smtp_user environment variable not found")
	}
	printDebugMessage(logger, debugMode, "using smtp_user: ", smtpUser)

	smtpPassword, found := os.LookupEnv("smtp_password")
	if !found {
		logger.Fatal("smtp_password environment variable not found")
		//return fmt.Errorf("smtp_password environment variable not found")
	}
	printDebugMessage(logger, debugMode, "using smtp_password [this value is hashed]:: ", smtpPassword)

	//var recipients []string = []string{"ahmad.alzamer@gmail.com", "ahmed.alzamer@gmail.com"}
	//subject := "Hello World"
	//body := fmt.Sprintf(`
	//	<html>
	//	<body>
	//		<h1>Hello from Go!</h1>
	//		<p>This is an <b>HTML email</b> sent using <i>gomail</i>.</p>
	//		<p>%s</p>
	//	</body>
	//	</html>
	//`, uuid.NewString())
	//sendEmail(subject, body, recipients)

	_server := server.NewMCPServer("SMTP server", "0.0.1", server.WithToolCapabilities(false))
	smtpTool := defineTool()
	_server.AddTool(smtpTool, partialSmtpToolHandler(debugMode, smtpUser, smtpPassword))
	if err := server.ServeStdio(_server); err != nil {
		fmt.Println("server error:", err)
	}
}

func defineTool() mcp.Tool {
	smtpTool := mcp.NewTool("email",
		mcp.WithDescription("sends email using smtp"),
		mcp.WithString("Subject", mcp.Required(), mcp.Description("subject of the email to be sent")),
		mcp.WithString("Body", mcp.Required(), mcp.Description("body of the email to be sent")),
		mcp.WithString("Recipients", mcp.Required(), mcp.Description("recipients of the email to be sent that are separated by commas")),
	)
	return smtpTool
}
func partialSmtpToolHandler(debugMode bool, smtpUser string, smtpPassword string) func(ctx context.Context, request mcp.CallToolRequest) (*mcp.CallToolResult, error) {
	return func(ctx context.Context, request mcp.CallToolRequest) (*mcp.CallToolResult, error) {
		return smtpToolHandler(ctx, request, debugMode, smtpUser, smtpPassword)
	}
}
func smtpToolHandler(ctx context.Context, request mcp.CallToolRequest, debugMode bool, smtpUser string, smtpPassword string) (*mcp.CallToolResult, error) {
	subject, err := request.RequireString("Subject")
	if err != nil {
		return mcp.NewToolResultError("Subject is required"), nil
	}

	body, err := request.RequireString("Body")
	if err != nil {
		return mcp.NewToolResultError("Body is required"), nil
	}

	recipients, err := request.RequireString("Recipients")
	if err != nil {
		return mcp.NewToolResultError("Recipients is required"), nil
	}
	logger := log.New(os.Stderr, "MCP_SERVER_SMTP: ", log.Ldate|log.Ltime|log.Lshortfile)

	printDebugMessage(logger, debugMode, "with subject: ", subject)
	printDebugMessage(logger, debugMode, "with recipients: ", recipients)
	printDebugMessage(logger, debugMode, "with body: ", body)

	err = sendEmail(subject, body, strings.Split(recipients, ","), smtpUser, smtpPassword)
	if err != nil {
		return mcp.NewToolResultError(err.Error()), nil
	}
	return mcp.NewToolResultText("successfully sent request to smtp server"), nil
}

func sendEmail(subject string, body string, recipients []string, smtpUser string, smtpPassword string) error {

	m := mail.NewMessage()

	// Set email headers
	m.SetHeader("From", smtpUser)
	m.SetHeader("To", recipients...)
	m.SetHeader("Subject", subject)

	// Set HTML body
	m.SetBody("text/html", body)

	// Optionally add an attachment
	// m.Attach("/path/to/your/file.pdf")

	// Set up the SMTP dialer
	d := mail.NewDialer("smtp.gmail.com", 587, smtpUser, smtpPassword)

	// Send the email
	if err := d.DialAndSend(m); err != nil {
		log.Fatal(err)
	}

	//fmt.Println("Email sent successfully with gomail!")
	return nil
}

func hashPassword(password string) string {
	// 1. Convert the input string to a byte slice
	data := []byte(password)

	// 2. Compute the SHA-512 hash
	hash := sha512.Sum512(data) // Sum512 returns a [64]byte array

	// 3. Base64 encode the hash
	encodedHash := base64.StdEncoding.EncodeToString(hash[:]) // Convert array to slice before encoding

	return encodedHash

}

func contains(s []string, e string) bool {
	for _, a := range s {
		if a == e {
			return true
		}
	}
	return false
}
