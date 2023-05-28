# Youth Council Project
### Team Members
- Alexia Cismaru
- Dorothy Modrall Sperling
- Filip Nowak
- Nikola Velikov
- Paul Ashioya
## Project Description
The Youth Council Project is a web application that allows young people to express their ideas on how to improve their community

## Project Setup

### Setting up

1. Clone the repository
2. Install the dependencies
3. Create a database

- (PROD PROFILE)Create a PostgreSQL database called "ycpdb" -> Username: "postgres" Password: "postgres"
- (DEV PROFILE) Create an H2 database called "ycpdbdev" -> Username: "sa" Password: ""

4. Open your terminal
5. Navigate to root folder of the project
6. Run the following command: `./gradlew bootRun`

### Tips

- user:lars password:lars is the general admin, you can access the page by going to localhost:8080/login and then it
  will redirect you to the dashboard
- user:admina1234 password:admin is the admin of antwerp youth council, navigate to antwerpyouthcouncil.local:8080/login
  and log in
