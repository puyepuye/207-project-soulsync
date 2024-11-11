# 💌 SOULSYNC  
### CSC207FW24 Course Final Project

## 📜 Contributing Conventions

When making contributions, please follow the commit message conventions below to keep the project organized and maintainable:

> **Commit Message Format**: `type: message`

### Commit Types
- `fix:` - Use for bug fixes.
- `feat:` - Use for new features.
- `chore:` - Use for maintenance tasks (e.g., updating dependencies, configuration changes).

```mermaid
erDiagram
    User {
        String userName
        String password
        String image
        String fullname
        String location
        String gender
        List preferredGender
        Date dateOfBirth
        Map preferredAge
        String bio
        Map preferences
        List tags
        List matched
        List swipedRight
        List swipedLeft
        List swipedRightOn
    }

    Matches {
        ObjectId userAId
        ObjectId userBId
        Date matchDate
        Boolean isActive
    }

    User ||--o{ Matches : "has matched with"
    Matches ||--|| User : "involves"

```
## Tasks



## 🌟 Contributors
1. **Yi-An Chu** (Kimi)
2. **Sataphon Obra** (PF)
3. **Lapatrada Jaroonjetjumnong (Claire)**
4. **Kay Zin Thant** (Yolanda)
5. **Thitiwut Pattanasuttinont** (Mac)


## Notes
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