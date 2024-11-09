# 💌 SOULSYNC  
### CSC207FW24 Course Final Project

## 📜 Contributing Conventions

When making contributions, please follow the commit message conventions below to keep the project organized and maintainable:

> **Commit Message Format**: `type: message`

### Commit Types
- `fix:` - Use for bug fixes.
- `feat:` - Use for new features.
- `chore:` - Use for maintenance tasks (e.g., updating dependencies, configuration changes).


## ✅ TODOs

#### 1. Verify `MainWithDB` Functionality
- Ensure that `MainWithDB` still runs as expected (from Lab 5).
- Re-clone and re-run if needed.

#### 2. Create a New Branch with Your UTORid
- Create and switch to a branch with your UTORid as the branch name.

```bash
  git branch chuyian
  git switch chuyian
  git branch
   ```

4. Try adding your name under Contributors in ReadME and make a Pull Request. Happy!

```bash
  git add .
  git commit -m "chore add ----"
  git push --set-upstream origin branchname
   ```
Follow pull request instructions in terminal, make sure "branch has no conflicts with the base branch".
      ![img.png](img.png)

## Tasks
### Phase 1: Research, Prepare DB & entities, new Login & SignUp

[Person 1]
- [Medium] Research functionalities and usages of Messaging API. how does it work? does it store chat data, what functionalities it has, how to integrate with javaswing shit etc. Implement a test chat.

[Person 2]
- [Easy] /entity: add image, fullName, location, gender, date of birth, tags, preferences, bio, matched, saved, also change all files in /entity accordingly
- [Medium] Configure /data_access for our project
- [Medium] Figure out Database and storing data, generate mock data for testing.

[Person 3]
- [Medium] /view/LoginView: implement UI
- [Medium] /interface_adapter/login: implement all

[Person 4 & 5]
- [Medium] /use_case/signup: implement everything for image, fullName, location, gender, date of birth, tags, preferences, bio
- [Hard] /view/SignupView: implement UI
- [Medium] /interface_adapter/signup: implement all

### Phase 2: Add new features - Change Profile Page
- [Medium] Implement ChangeProfile
  - `app/ChangeProfileUseCaseFactory.java`
  - `use_case/change_profile/ChangeProfileInputBoundary.java`
  - `use_case/change_profile/ChangeProfileInputData.java`
  - `use_case/change_profile/ChangeProfileInteractor.java`
  - `use_case/change_profile/ChangeProfileOutputBoundary.java`
  - `use_case/change_profile/ChangeProfileOutputData.java`
  - `use_case/change_profile/ChangeProfileUserDataAccessInterface.java`

- [Medium] Implement ChangeProfileController for ChangeProfile features
  - `interface_adapter/change_profile/ChangeProfileEmailController.java`
  - `interface_adapter/change_profile/ChangeProfilePasswordController.java`
  - `interface_adapter/change_profile/ChangeProfileBioController.java`
  - `interface_adapter/change_profile/ChangeProfileCountryController.java`
  - `interface_adapter/change_profile/ChangeProfileGenderController.java`
  - `interface_adapter/change_profile/ChangeProfileDOBController.java`
  - `interface_adapter/change_profile/ChangeProfileTagsController.java`
  - `interface_adapter/change_profile/ChangeProfilePreferencesController.java`
  - `interface_adapter/change_profile/ChangeProfilePreferredGenderController.java`
  - `interface_adapter/change_profile/ChangeProfilePreferredLocationController.java`
  - `interface_adapter/change_profile/ChangeProfilePreferredAgeRangeController.java`

- [Medium] Implement Shared Components for ChangeProfile
  - `interface_adapter/change_profile/ChangeProfilePresenter.java`
  - `interface_adapter/change_profile/ChangeProfileState.java`
  - `interface_adapter/change_profile/ChangeProfileViewModel.java`

- [Medium] Implement Views for ChangeProfile Components
  - `interface_adapter/change_profile/ChangeProfileView.java`

- [Medium] Add Unit Tests for ChangeProfile Components [Later]
  - `test/ChangeProfileUseCaseFactoryTest.java`
  - `test/use_case/change_profile/ChangeProfileInteractorTest.java`
  - `test/interface_adapter/change_profile/ChangeProfileControllerTest.java`
  - `test/interface_adapter/change_profile/ChangeProfilePresenterTest.java`
  - `test/interface_adapter/change_profile/ChangeProfileStateTest.java`
  - `test/interface_adapter/change_profile/ChangeProfileViewModelTest.java`
  - `test/interface_adapter/change_profile/ChangeProfileViewTest.java`

## [WORK IN PROGRESS, havent finished thinking abt it]
- [Medium/Hard] Implement CompatibilityScore & fetchProfiles
  - `app/CompatibilityScoreUseCaseFactory.java`
  - `use_case/recommendation/compatibilityScoreInputBoundary.java`
  - `use_case/recommendation/compatibilityScoreInputData.java`
  - `use_case/recommendation/compatibilityScoreInteractor.java`
  - `use_case/recommendation/compatibilityScoreOutputBoundary.java`
  - `use_case/recommendation/compatibilityScoreOutputData.java`
  - `use_case/recommendation/compatibilityScoreUserDataAccessInterface.java`
  - `app/FetchProfilesUseCaseFactory.java`
  - `use_case/recommendation/FetchProfilesInputBoundary.java`
  - `use_case/recommendation/FetchProfilesScoreInputData.java`
  - `use_case/recommendation/FetchProfilesScoreInteractor.java`
  - `use_case/recommendation/FetchProfilesScoreOutputBoundary.java`
  - `use_case/recommendation/FetchProfilesScoreOutputData.java`
  - `use_case/recommendation/FetchProfilesScoreUserDataAccessInterface.java`
  -



## 🌟 Contributors
1. **Yi-An Chu** (Kimi)
2. **Sataphon Obra** (PF)
3. **Lapatrada Jaroonjetjumnong (Claire)**
4. **Kay Zin Thant** (Yolanda)
5. **Thitiwut Pattanasuttinont** (Mac)
