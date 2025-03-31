
#  Project App User Management: List, Detail, Add, Update Screens with MVVM

## 1. Cấu trúc Dự Án

Dự án này được phát triển bằng **Android Native** (Kotlin), với mô hình **MVVM** (Model-View-ViewModel) để quản lý logic và dữ liệu dễ dàng.
Kiến trúc **Clean Architecture** được áp dụng với 3 lớp: **Presentation**, **Domain**, **Data**.
### Các thành phần chính:
- **Presentation**: Quản lý UI .
- **Domain**: Xử lý logic business.
- **Data**: Xử lý Data.

### Các màn hình chính:
- **Màn hình home (Home)**: Hiển thị danh sách User.
- **Màn hình add (AddScreen)**: Thêm user.
- **Màn hình edit (EditScreen)**: Cập nhập user.
- **Màn hình detail (DetailScreen)**: Hiển thị chi tiết thông tin User.

### Cấu trúc thư mục
```
AppUserManagement
│── app
│   ├── src/main/java/com/example/apptest
│   │   ├── data/        # Lớp Data (Repository, API, Database)
│   │   ├── domain/      # Lớp Domain (Use Cases, Models)
│   │   ├── presentation/# Lớp Presentation (ViewModel, UI - Compose)
│   │   ├── di/          # Dependency Injection (Hilt)
│   │   ├── utils/       # Các hợ trợ chung
│   ├── res/            # Tài nguyên (layout, drawable, values...)
│   ├── AndroidManifest.xml
│
├── build.gradle        # Cấu hình build
└── settings.gradle     # Cấu hình dự án
```

### Link demo app: https://drive.google.com/file/d/1qzdYnlYkKUoaWBdAUZkOPa7FVy0S9zr0/view?usp=sharing


### Link file APK: https://drive.google.com/file/d/14ExetqHSDmL0qt8_VueVLnT4tY6nlTm-/view?usp=sharing

