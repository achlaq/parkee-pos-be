# 🚀 pos-api-java

Aplikasi backend menggunakan **Spring Boot** dengan REST API untuk mengelola aplikasi parkee-pos.

---

## 🛠️ Tech Stack
- [Java 17+](https://adoptium.net/)
- [Spring Boot 3.x](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- Database: PostgreSQL, FlyAway

---

## 📦 Installation & Running

### 1. Clone repository
```bash
git clone https://github.com/achlaq/parkee-pos-be.git
cd parkee-pos-be
```

### 2. Configure Database
```sql
CREATE DATABASE parkee_pos;
```

```yaml
spring.datasource.url=jdbc:postgresql://localhost:[your-port]/parkee_pos
spring.datasource.username=[your-username]
spring.datasource.password=[your-password]
```

### 3. Jalankan aplikasi
```bash
./mvnw spring-boot:run
```

👉 Access on http://localhost:9090