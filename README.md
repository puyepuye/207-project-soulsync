# 💌 SOULSYNC  
### CSC207 Fall 2024 Course Final Project
A dating app created in Java as the final project for the course, CSC207 Software Design, during the fall 2024 semester the university of Toronto. This app allows users to create an account, find matches and chat with like minded people! (cause Toronto is a really big city and it's hard to meet people)

## Table of Contents:
- [Features](#features)
- [Installation](#installation)
- [Usage Guide](#usage-guide)
- [Contributors](#contributors)
- [Contributing Conventions](#contributing-conventions)
- [Feedback](#feedback)
- [License](#license)

## Features
- Users can create an account with their personal information and start matching with other users
- Users can select other users to match with Tinder swipe style
- Users can be matched based on preferences or through our in house Fengshui compatability calculator!
- After a user has been matched, users can chat with the other matched user in real time.


## Installation
As a forewarning, this app is still in the prototyping phase, so the user would be required to manually set up a database on MongdoDB, their own chat app in SendBird's API service, as well as setting up an account for cloudinary. #TODO add cloudinary steps
1. Clone using `git clone https://github.com/puyepuye/207-project-soulsync.git`
2. Install ngrok from [here](https://ngrok.com)
3. Create a new sendbird account and project here [here](https://sendbird.com)
4. Create a cloudinary account and project here [here](https://cloudinary.com)
5. Create a .env include the following information from the services above:
```
SENDBIRD_API_KEY={YOUR API KEY HERE}
SENDBIRD_APP_ID={YOUR APP ID HERE}
MONGODB_URI={URI TO MONGODB CLUSTER}
USERNAME={YOUR MONGODB USERNAME}
PASSWORD={YOUR MONGODB PASSWORD}
CLOUDINARY_URL= {YOUR CLOUDINARY URL}
CLOUD_NAME= {CLOUDINARY CLOUD NAME}
CLOUDINARY_API_KEY= {CLOUDINARY API KEY}
CLOUDINARY_API_SECRET= {CLOUDINARY API SECRET}
```
6. In the resources folder create a file called `app.properties` and add the following:
```
spring.data.mongodb.uri={YOUR MONGODB CLUSTER URI}
spring.data.mongodb.database={NAME OF YOUR DATABASE}
server.port={DESIRED PORT}
server.address={DESIRED RUN ADDRESS}
```
7. In IntelliJ, click on pom.xml -> maven -> reload project
8. You will need to setup one machine as a main server of the app, then tunnel your IP address by running `ngrok http 8080` in your terminal
9. In your sendbird dashboard, go to app -> chat -> settings -> features -> webhook and set the URl to be the machine you choose to be your server
10. Run that same line on all the connected clients
11. Copy the client URLs and paste it line by line into `clientUrls.txt`
12. Run the app from `MainwithDB.java` and enjoy!

## Usage guide:
1. Users can create an account from the sign up page
2. The user can then answer our questionare and build out their profile
3. After the use has built their profile then the user can start matching!
4. Once a user has matched they can start chatting with one another
5. Users can also calculate their compability with those they've matched with on the Compatibility page
7. Users can also edit their profiles to update their photos or other information.
<center>
<img width="300" alt="image" src="https://github.com/user-attachments/assets/fd7fc67f-6d71-41e9-855a-0d6729ed642a">
<img width="300" alt="image" src="https://github.com/user-attachments/assets/03028313-73c8-49b8-b4cc-0f07434ad780">
<img width="266" alt="image" src="https://github.com/user-attachments/assets/785b9880-fd8b-410d-8f99-48ca448f60a7">
<img width="732" alt="image" src="https://github.com/user-attachments/assets/7fe17823-82ee-48b2-83d6-fac8a47f3e86">
<img width="300" alt="image" src="https://github.com/user-attachments/assets/ae8b78ba-0e19-448b-ad4b-9ee7fc42e1c9">
<img width="300" height="400" alt="image" src="https://github.com/user-attachments/assets/4227efb9-f669-4c61-9132-36dcafa1a497">


*The UI pages in the same order mentioned*
</center>
    
## Contributors
1. **Yi-An Chu** (Kimi)
2. **Sataphon Obra** (PF)
3. **Lapatrada Jaroonjetjumnong (Claire)**
4. **Kay Zin Thant** (Yolanda)
5. **Thitiwut Pattanasuttinont** (Mac)

## Contributing Conventions

When making contributions, please follow the commit message conventions below to keep the project organized and maintainable:

> **Commit Message Format**: `type: message`

### Commit Types
- `fix:` - Use for bug fixes.
- `feat:` - Use for new features.
- `chore:` - Use for maintenance tasks (e.g., updating dependencies, configuration changes).

## Feedback 
If you have any ideas and want to make our app better please let us know [here](https://forms.gle/7X6y8R2i8789JkLY9) in this Google Forms.
Note: Please keep your criticisms constructive and remember that we're all humans :) Mac will reach back to you within one or two days and let you know what we can do from your feedback!

## Notes for connecting pages
1. After implementing view must add to MainWithDB set up with UseCaseFactory which follows flow:      
   - loginInteractor (execute which calles loginPresenter.prepareSuccessView
   - login interactor executi is inplementation of logininputBoundary.java
   - loginController calls loginUseCaseInteractor.exe
   - LoginView.java
   ```bash
          loginController.execute(
                    currentState.getUsername()
                    currentState.getPassword()
                   );
   ```
## License
This repository has a Creative Commons CC0-1.0 license
