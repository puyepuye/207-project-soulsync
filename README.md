# 💌 SOULSYNC  
### CSC207 Fall 2024 Course Final Project
A dating app created in Java as the final project for the course, CSC207 Software Design, during the fall 2024 semester the university of Toronto. This app allows users to create an account, find matches and chat with like minded people! (cause Toronto is a really big city and it's hard to meet people)

## Features:
- Users can create an account with their personal information and start matching with other users
- Users can select other users to match with Tinder swipe style
- Users can be matched based on preferences or through our in house Fengshui compatability calculator!
- After a user has been matched, users can chat with the other matched user in real time.

## Installation
1. Clone 
## 🌟 Contributors
1. **Yi-An Chu** (Kimi)
2. **Sataphon Obra** (PF)
3. **Lapatrada Jaroonjetjumnong (Claire)**
4. **Kay Zin Thant** (Yolanda)
5. **Thitiwut Pattanasuttinont** (Mac)


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
