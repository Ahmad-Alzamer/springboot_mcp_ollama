# Introduction
trying different things in the SpringBoot AI model.
this is just for testing and some of the code I just put together just to test how to creat custom MCP server and how to work with Spring AI.

# How to Run
1. run docker compose to bring needed containers up
    ```shell
    docker compose up -d
    ```
2. either comment out the email MCP server configuration in the application.yml (mcp.client.stdio.connections.email section) or  build the email MCP client that was built in golang
please note that the code is using gmail's smtp server
    ```shell
    # in the folder of the MCP server
    go build 
    ```
3. install the NPM dependencies and run the app locally in a different command window. in the folder: UI
    ```shell
    npm i
    npm run dev
    ```
4. run the java application. however, please note that you need to set the below env variables
    * path_to_go_project=<path to the root folder of the folder where you extracted the git repo>
    * smtp_password=<app password from Google>
    * smtp_user=<your email with gmail>
    * debug_mode=<true if you want the go smtp MCP server to print debugging statements>




