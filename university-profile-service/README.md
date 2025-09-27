# Profile Service - Sayeem

This project is part of **Task-3 (TIES4560, 2025)**.  
It implements a **REST Web Service** using **Jersey** in **Java** with **Tomcat 9**.

---

## 📌 Overview
The **Profile Service** is designed as part of the university’s system to manage and expose profile-related data through RESTful APIs. It follows the principles of REST architecture and is deployed on Tomcat 9.

Key aspects:
- Implemented using **Jersey framework**.
- Follows **REST standards** with proper HTTP methods.
- Uses **JSON** as the primary format for communication.
- Designed with modular resources for scalability.

---

## 🛠 Features
- **Resources**
  - At least **2 upper-level resources** (e.g., `/profiles`, `/students`)
  - At least **2 nested resources** (e.g., `/profiles/{id}/details`, `/students/{id}/courses`)
- **HTTP Methods**
  - GET → Retrieve resources
  - POST → Create new resources
  - PUT → Update existing resources
  - DELETE → Remove resources
- **Implementation Details**
  - Path variables support
  - Query parameters handling
  - Proper HTTP status codes
  - Custom exception handler
  - HATEOAS (Hypermedia links in responses)

---

## 📂 Project Structure
```
Profile Service - Sayeem/
 ├── university-profile-service/     # Source code
 ├── slides.pptx                     # Presentation explaining design & findings
 ├── profile-service.war             # Deployable WAR file
 └── README.md                       # Project documentation
```

---

## 🚀 Setup Instructions

### Prerequisites
- **Java 8+**
- **Apache Tomcat 9**
- **Maven** (if using Maven build)
- **Git**

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/mohammadsayeemsadathossain/StudyStart-JYU-Task_3.git
   ```

2. Navigate into the project:
   ```bash
   cd "StudyStart-JYU-Task_3/Profile Service - Sayeem/university-profile-service"
   ```

3. Build the project (if Maven is used):
   ```bash
   mvn clean install
   ```

4. Deploy the WAR file:
   - Copy `profile-service.war` to Tomcat’s `webapps/` directory.
   - Start Tomcat.
   - Access via:  
     ```
     http://localhost:8080/university-profile-service/api/...
     ```

---

## 📊 Assignment Notes
- Developed as part of **TIES4560 Task-3 (2025)**.
- Focus on learning and implementing **REST Web Services**.
- Includes:
  - API design
  - REST implementation
  - Documentation
  - WAR deployment package
- Presentation slides explain:
  - API design & functionality
  - Implementation details
  - Bottlenecks & solutions

---

## 👤 Author
**Sayeem**  
Task: **Profile Service** (REST API with Jersey, Tomcat 9)

---
