# Build APK on GitHub (GitHub Actions)

Workflow file: `.github/workflows/android-build.yml`

## Download the APK

1) Push this project to a GitHub repo (branch `main` or `master`).
2) Go to **Actions**.
3) Open **Build Debug APK**.
4) Open the latest run.
5) In **Artifacts**, download `app-debug-apk`.
6) Inside it, you will find: `app-debug.apk`.

## Notes

- The workflow uses **JDK 17** and installs **Gradle 8.2** on the runner.
- If you later add the Gradle Wrapper (`gradlew`), you can switch the workflow command to `./gradlew :app:assembleDebug`.
