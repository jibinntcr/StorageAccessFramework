

#  Storage Access Framework (SAF) 

### Overview

Modern Android apps utilize **Scoped Storage** to protect user privacy. Instead of requesting broad permissions to read every file on a device, we use SAF to let the user pick a specific file. The system then provides the app with a secure **URI** (a temporary link).

### Key Components in this Lab

* **The Launcher**: Uses `ActivityResultContracts.OpenDocument()` to trigger the system file picker without requiring manual manifest permissions.

* **MIME Type Filtering**: Restricts the picker to specific formats, such as PDFs (`application/pdf`) and images (`image/*`).

* **Persistable Permissions**: Uses `takePersistableUriPermission` to ensure the app maintains access to the selected file even after the device restarts.

* **Jetpack Compose UI**: A self-explanatory interface that displays the current connection status and the file's URI.


### How to Run the Implementation

1. **Launch**: Tap the **"Browse & Select File"** button. This opens the system picker.

2. **Selection**: Choose a PDF or image from your device storage.

3. **Verification**: The UI will update to show the `lastPathSegment` of the URI. A Toast message will confirm that secure access has been granted.

4. **Testing Persistence**: Close the app and reopen it; the permission logic ensures the link remains valid.


### Storage Rule of Thumb

| Data Type | Recommended Tool | Persistence Level |
| --- | --- | --- |
| **Simple Settings** (Username, Dark Mode) | DataStore 

 | Persistent (Key-Value) |
| **Private Files** (App Cache, Secret Notes) | Internal Storage 

 | App-Private |
| **Shared Content** (PDFs, Media Exports) | SAF + URI 

 | User-Controlled |

---

### Troubleshooting

* **No Files Appearing**: Ensure the files on your device match the requested MIME types (PDF or Images).

* **Permission Denied**: SAF handles permissions automatically; if access fails, ensure the `takePersistableUriPermission` call is present in your result launcher.

 
