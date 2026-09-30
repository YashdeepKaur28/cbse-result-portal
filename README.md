# CBSE Student Result Management System

A full-stack web application for managing and viewing CBSE student academic results securely, built with **J2EE (Servlets), JDBC, and MySQL**, and deployed on **Oracle WebLogic Server** using a JNDI DataSource.

## 📖 Overview

The **CBSE Student Result Management System** is a Java web application that allows administrators to manage student records and academic results, while students can securely view their own results. It demonstrates core enterprise Java concepts including **Servlets, JDBC, JNDI, session management, cookies, JavaMail, and Excel export**.

## ✨ Features

- 🔐 **Role-based login** (Admin / Student) with session management
- ➕ **Add Student** — insert new student records with marks
- ✏️ **Update Marks** — modify marks and auto-recalculate total, percentage, and grade
- 🗑️ **Delete Student** — remove records by ID
- 🔍 **Search Student** — view a detailed result card for a single student
- 📊 **Display All Students** — tabular view of all records
- 📥 **Export to Excel** — download single or all student results as `.xls`
- 📧 **Email Notifications** — automatic email on user registration (JavaMail API)
- 🍪 **Remember Me** — persistent login using cookies
- 🧮 **Automatic Grade Calculation** — based on CBSE percentage bands
- 🗄️ **JNDI DataSource** — connection pooling via WebLogic Server (`tindi`)
- 🛡️ **Input Validation** — marks restricted to 0–100 with server-side checks
  
## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java (Core Java, Servlets) |
| Web | J2EE, HTML, CSS, JavaScript |
| Database | MySQL (via JDBC) |
| Server | Oracle WebLogic Server 12c |
| APIs | JavaMail (registration emails), Servlet API |
| Architecture | MVC-style with Servlets + JNDI DataSource |
| Tools | Eclipse IDE, Apache Tomcat (optional), Git |

---

## 📂 Project Structure

```
cbse-result-portal/
│
├── src/                          → Java source files
│   ├── AddStudentServlet.java
│   ├── DeleteStudentServlet.java
│   ├── UpdateStudentServlet.java
│   ├── SearchStudentServlet.java
│   ├── DisplayAllServlet.java
│   ├── ExcelServlet.java
│   ├── LoginServlet.java
│   ├── RegisterServlet.java
│   ├── LogoutServlet.java
│   ├── GradeUtil.java
│   ├── Student.java
│   └── Email.java
│   ├── index.html         → Web resources
│   ├── login.html
│   ├── register.html
│   ├── addStudent.html
│   ├── deleteStudent.html
│   ├── updateStudent.html
│   ├── searchStudent.html
│   ├── displayAll.html
│   └── WEB-INF/
|       └── /classes
│       └── web.xml
│
|_____


## 🗄️ Database Schema

Run the following SQL in MySQL to set up the database:

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS cbse_db;
USE cbse_db;

-- Users table for authentication
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Student results table
CREATE TABLE cbse_result (
    student_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    class VARCHAR(20) NOT NULL,
    math_marks DOUBLE,
    science_marks DOUBLE,
    english_marks DOUBLE,
    hindi_marks DOUBLE,
    sst_marks DOUBLE,
    total_marks DOUBLE,
    percentage DOUBLE,
    grade VARCHAR(5)
);
```

## 🧮 Grade Calculation Logic

Grades are calculated in `GradeUtil.java` based on CBSE percentage bands:

| Percentage Range | Grade |
|------------------|-------|
| ≥ 91 | A1 |
| ≥ 81 | A2 |
| ≥ 71 | B1 |
| ≥ 61 | B2 |
| ≥ 51 | C1 |
| ≥ 41 | C2 |
| ≥ 33 | D |
| < 33 | E |

**Formulas:**
- `Total = Math + Science + English + Hindi + SST`
- `Percentage = Total / 5.0`

---

## 🔗 Servlet Mappings (from `web.xml`)

| URL Pattern | Servlet | Purpose |
|-------------|---------|---------|
| `/add` | `AddStudentServlet` | Add new student |
| `/delete` | `DeleteStudentServlet` | Delete by ID |
| `/update` | `UpdateStudentServlet` | Update marks |
| `/search` | `SearchStudentServlet` | Search by ID |
| `/displayall` | `DisplayAllServlet` | List all students |
| `/excel` | `ExcelServlet` | Export to Excel |
| `/login` | `LoginServlet` | User login |
| `/register` | `RegisterServlet` | New user registration |
| `/logout` | `LogoutServlet` | Invalidate session |

---

## 📸 Screenshots

<img width="1140" height="512" alt="image" src="https://github.com/user-attachments/assets/c41fb1c0-fea7-45c1-ac47-8220f6fd055e" />


## 🚀 Future Enhancements

- [ ] Migrate to **Spring Boot + Spring Data JPA** for cleaner architecture
- [ ] Add **JWT-based authentication** instead of session cookies
- [ ] Build a **REST API** for mobile clients
- [ ] Add **PDF result card generation**
- [ ] Containerize with **Docker** and deploy to AWS
- [ ] Add unit tests with **JUnit + Mockito**
- [ ] Replace JSP/HTML with **React** frontend


## 👩‍💻 Author

**Yashdeep Kaur**
- 🎓 B.Tech CSE, Punjab University, Patiala (2026)
- 💼 Java Full Stack Trainee @ CodeSquadz
- 📧 Email: ykdeep2453@gmail.com
- 🔗 LinkedIn: [yashdeep-kaur-16aa083b1](https://linkedin.com/in/yashdeep-kaur-16aa083b1)
- 🐙 GitHub: [@YashdeepKaur28](https://github.com/YashdeepKaur28)


⭐ If you found this project useful, consider giving it a star!
