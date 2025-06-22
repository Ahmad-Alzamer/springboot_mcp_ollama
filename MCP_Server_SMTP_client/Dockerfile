# Stage 1: Build the Go application
FROM golang:1.23-alpine AS builder
WORKDIR /app
COPY go.mod go.sum ./
RUN go mod download
COPY . .
RUN CGO_ENABLED=0 GOOS=linux go build -o /app/myapp .

# Stage 2: Create the final, lean image
FROM alpine:latest
WORKDIR /app
COPY --from=builder /app/myapp .
ENV debug_mode=false
ENV smtp_user=CHANGE_ME
ENV smtp_password=CHANGE_ME
CMD ["./myapp"]