# 💌 SOULSYNC  
### CSC207 Fall 2024 Course Final Project
A dating app created in Java as the final project for the course, CSC207 Software Design, during the fall 2024 semester the university of Toronto. This app allows users to create an account, find matches and chat with like minded people! (cause Toronto is a really big city and it's hard to meet people)

## Table of Contents:
[Features](#Features:)

## Features:
- Users can create an account with their personal information and start matching with other users
- Users can select other users to match with Tinder swipe style
- Users can be matched based on preferences or through our in house Fengshui compatability calculator!
- After a user has been matched, users can chat with the other matched user in real time.

## Installation
As a forewarning, this app is still in the prototyping phase, so the user would be required to manually set up a database on MongdoDB, their own chat app in SendBird's API service, as well as setting up an account for cloudinary. #TODO add cloudinary steps
1. Clone using `git clone https://github.com/puyepuye/207-project-soulsync.git`
2. Install ngrok from [here](https://ngrok.com)
3. Create a .env include the following:
4. Create a new sendbird account and project here [here](https://sendbird.com)
```
SENDBIRD_API_KEY={YOUR API KEY HERE}
SENDBIRD_APP_ID={YOUR APP ID HERE}
MONGODB_URI={URI TO MONGODB CLUSTER}
USERNAME={YOUR MONGODB USERNAME}
PASSWORD={YOUR MONGODB PASSWORD}
```
5. In IntelliJ, click on pom.xml -> maven -> reload project
6. You will need to setup one machine as a main server of the app, then tunnel your IP address by running `ngrok http 8080` in your terminal
7. In your sendbird dashboard, go to app -> chat -> settings -> features -> webhook and set the URl to be the machine you choose to be your server
8. Run that same line on all the connected clients
9. Copy the client URLs and paste it line by line into `clientUrls.txt`
10. Run the app from `MainwithDB.java` and enjoy!

## Usage guide: #TODO
    
## 🌟 Contributors
1. **Yi-An Chu** (Kimi)
2. **Sataphon Obra** (PF)
3. **Lapatrada Jaroonjetjumnong (Claire)**
4. **Kay Zin Thant** (Yolanda)
5. **Thitiwut Pattanasuttinont** (Mac)

## 📜 Contributing Conventions

When making contributions, please follow the commit message conventions below to keep the project organized and maintainable:

> **Commit Message Format**: `type: message`

### Commit Types
- `fix:` - Use for bug fixes.
- `feat:` - Use for new features.
- `chore:` - Use for maintenance tasks (e.g., updating dependencies, configuration changes).

## Feedback #TODO

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
