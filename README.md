## Система "Запись ко врачу" на Kafka

Микросервисная система для записи пациентов на приём к врачу с использованием Kafka, PostgreSQL, Docker и Spring Boot.

### Технологии

- **Язык / фреймворк**: Java 17, Spring Boot 3 (Web, Data JPA, Validation, Spring for Apache Kafka)
- **Брокер сообщений**: Apache Kafka (+ Zookeeper, образ Confluent)
- **База данных**: PostgreSQL
- **Контейнеризация**: Docker, Docker Compose
- **БД-доступ**: Spring Data JPA, Hibernate
- **Формат обмена**: JSON (HTTP, Kafka сообщения)

## Архитектура

- **API Service (`api-service`)**
  - Открытый HTTP API.
  - Приём запросов на запись → отправка сообщений в Kafka (producer).
  - Проксирование запросов поиска и отчётов в Data Service (через HTTP).

- **Data Service (`data-service`)**
  - Kafka consumer: читает сообщения о записях.
  - Сохраняет данные в PostgreSQL (таблицы `patients`, `doctors`, `appointments`).
  - Предоставляет HTTP API для:
    - поиска записей;
    - формирования отчётов.

## Эндпоинты

### API Service (порт 8080)

База URL: `http://localhost:8080/api`

- **Создать запись на приём**

  - `POST /api/appointments`
  - Тело запроса (JSON):

    ```json
    {
      "patientName": "Иванов Иван",
      "doctorName": "Петров Петр",
      "doctorSpecialty": "Терапевт",
      "appointmentDateTime": "2026-03-20T10:30:00",
      "reason": "Плановый осмотр"
    }
    ```

  - Результат: `202 Accepted` (запись отправлена в Kafka, будет обработана асинхронно).

- **Поиск записей**

  - `GET /api/appointments`
  - Параметры (все опциональные):
    - `patientName` — фильтр по имени пациента (substring, регистронезависимо)
    - `doctorName` — фильтр по имени врача
    - `from` — дата-время от (формат `YYYY-MM-DDTHH:MM:SS`)
    - `to` — дата-время до
  - Пример:

    `GET /api/appointments?patientName=Иванов&from=2026-03-01T00:00:00&to=2026-03-31T23:59:59`

- **Отчёты**

  - `GET /api/reports/appointments-per-day`  
    Количество записей по дням.

  - `GET /api/reports/top-patients`  
    Топ‑10 пациентов по количеству записей.

  - `GET /api/reports/top-doctors`  
    Топ‑10 врачей по количеству записей.

---

### Data Service (порт 8081)

База URL: `http://localhost:8081/api`

- **Поиск записей**: `GET /api/appointments/search`  
  Параметры те же, что у `GET /api/appointments` в API Service.

- **Отчёты**:
  - `GET /api/reports/appointments-per-day`
  - `GET /api/reports/top-patients`
  - `GET /api/reports/top-doctors`

Ответы — JSON‑массивы с полями дня/пациента/врача и количеством записей.

## Как запустить

### 1. Подготовить `.env`

В корне проекта есть файл-пример:

```text
.env.example
```

### 2. Собрать и запустить через Docker Compose

Из корневой директории проекта (где лежит `docker-compose.yml`):

```bash
docker compose build
docker compose up
```

Будут подняты сервисы:

- `zookeeper`
- `kafka`
- `postgres`
- `data-service` (порт 8081)
- `api-service` (порт 8080)
