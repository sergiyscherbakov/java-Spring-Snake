# 🐍 Гра Змійка на Spring Boot

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=java) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen?style=flat&logo=springboot) ![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=flat&logo=apachemaven) ![WebSocket](https://img.shields.io/badge/WebSocket-STOMP-red?style=flat) ![License](https://img.shields.io/badge/License-MIT-yellow?style=flat)

## Автор

**Розробник:** Сергій Щербаков
**Email:** sergiyscherbakov@ukr.net
**Telegram:** @s_help_2010

### 💰 Підтримати розробку
Задонатити на каву USDT (BINANCE SMART CHAIN):
**`0xDFD0A23d2FEd7c1ab8A0F9A4a1F8386832B6f95A`**

---

Класична гра "Змійка" реалізована за допомогою Java Spring Boot з використанням WebSocket для оновлень в реальному часі.

## 📋 Опис

Проста та захоплююча гра "Змійка" з красивим веб-інтерфейсом. Гра працює в браузері та використовує WebSocket для комунікації між клієнтом та сервером.

## 🚀 Технології

- **Backend**: Java 17, Spring Boot 3.2.0
- **Frontend**: HTML5, CSS3, JavaScript
- **Комунікація**: WebSocket (STOMP)
- **Шаблонізатор**: Thymeleaf
- **Збірка**: Maven

## 📦 Вимоги

- Java 17 або вище
- Maven 3.6+

## 🛠️ Встановлення та запуск

### Клонування репозиторію

```bash
git clone https://github.com/sergiyscherbakov/java-Spring-Snake.git
cd java-Spring-Snake
```

### Запуск за допомогою Maven

```bash
mvn spring-boot:run
```

### Запуск скомпільованого JAR

```bash
mvn clean package
java -jar target/snake-game-1.0.0.jar
```

Додаток буде доступний за адресою: **http://localhost:8080**

## 🎮 Як грати

1. Відкрийте браузер та перейдіть на http://localhost:8080
2. Натисніть кнопку **"Почати гру"**
3. Використовуйте клавіші керування:
   - **⬆️ Стрілка вгору** або **W** - рух вгору
   - **⬇️ Стрілка вниз** або **S** - рух вниз
   - **⬅️ Стрілка вліво** або **A** - рух вліво
   - **➡️ Стрілка вправо** або **D** - рух вправо
4. Збирайте червоні яблука для збільшення рахунку
5. Уникайте стін та власного хвоста!

## 📁 Структура проекту

```
java-Spring-Snake/
├── src/
│   └── main/
│       ├── java/
│       │   └── ua/snake/game/
│       │       ├── SnakeGameApplication.java
│       │       ├── config/
│       │       │   └── WebSocketConfig.java
│       │       ├── controller/
│       │       │   └── GameController.java
│       │       ├── model/
│       │       │   ├── Direction.java
│       │       │   ├── Food.java
│       │       │   ├── GameState.java
│       │       │   ├── Point.java
│       │       │   └── Snake.java
│       │       └── service/
│       │           └── GameService.java
│       └── resources/
│           ├── static/
│           │   ├── css/
│           │   │   └── style.css
│           │   └── js/
│           │       └── game.js
│           ├── templates/
│           │   └── index.html
│           └── application.properties
├── pom.xml
└── README.md
```

## ⚙️ Особливості

- ✅ Реалізація гри на Java з використанням Spring Boot
- ✅ WebSocket комунікація для плавного геймплею
- ✅ Красивий та адаптивний інтерфейс
- ✅ Підтримка декількох одночасних ігрових сесій
- ✅ Підрахунок очок
- ✅ Модальне вікно завершення гри
- ✅ Можливість перезапуску гри

## 📄 Ліцензія

Цей проект ліцензовано під MIT License - дивіться файл [LICENSE](LICENSE) для деталей.