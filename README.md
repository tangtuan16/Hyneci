# Library Management System

##  Công nghệ sử dụng
- **Backend API:** Java, Spring Boot, Spring Data JPA, Spring Security, JWTs  
- **Email Service:** Java Mail Sender  
- **Database:** MySQL  
- **Client:** Spring Boot (HTML, CSS, JS, Thymeleaf)  
- **Admin:** Java Swing  
- **Deployment:** Docker, Nginx  
- **Documentation:** Swagger  

---

##  Mô tả dự án
Hệ thống quản lý thư viện bao gồm **API – Admin – Client**, giúp quản lý và mượn sách một cách tiện lợi, phân quyền rõ ràng giữa **Admin** và **Người dùng (Client)**.  

###  1. API (Spring Boot – RESTful)
- Xây dựng theo mô hình **MVC** và chuẩn **RESTful API**.  
- Cung cấp đầy đủ **CRUD (GET, POST, PUT, DELETE)** cho cả **Admin** và **Client**.  
- Tích hợp **Spring Security + JWT** để:
  - Xác thực người dùng.  
  - Phân quyền dựa trên **Role** (Admin/User).  
- Tài liệu API được quản lý qua **Swagger UI**.  

###  2. Client (Spring Boot + Web)
- Tương tác với **API** để lấy dữ liệu.  
- Các chức năng đã hoàn thành:
  - Đăng ký, Đăng nhập.  
  - Quên mật khẩu / Reset mật khẩu qua Email.  
- Đang phát triển:  
  - Trang chủ hiển thị danh sách sách từ **database**.  

###  3. Admin (Java Swing)
- Ứng dụng Desktop đơn giản dành cho **Admin**.  
- Các chức năng chính:
  - **CRUD Sách (Product Management)**.  
  - **Quản lý tài khoản người dùng**.  

---

##  Triển khai hệ thống
Hệ thống được **đóng gói bằng Docker** và chạy qua **Nginx**:
- **API Service:** expose qua 1 cổng riêng.  
- **Client Service:** expose qua 1 cổng riêng.  
- **MySQL:** chạy trong container riêng.  
- **Swagger UI:** hỗ trợ kiểm thử API.  

---

##  Chức năng chính
✔️ Quản lý người dùng (Admin/User).  
✔️ Quản lý sách (CRUD).  
✔️ Đăng nhập / Đăng ký / Xác thực bằng JWT.  
✔️ Reset mật khẩu qua Email.  
✔️ Quản lý mượn/trả sách.  
✔️ Đóng gói & triển khai bằng Docker.  

---

##  Kiến trúc hệ thống
Client (Spring Boot Web) <--> API (Spring Boot REST) <--> MySQL
↑ ↑
| |
Người dùng (Web) Admin (Java Swing)


---

##  Tài liệu API
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  

---

##  Cách chạy dự án
1. Clone dự án:  
   ```bash
   git clone https://github.com/tangtuan16/Hyneci.git
   cd Hyneci


Build & chạy bằng Docker:

docker-compose up --build


Truy cập:

Client: http://localhost

API: http://localhost:8080

Swagger: http://localhost:8080/swagger-ui.html
