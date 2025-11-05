// Глобальні змінні
let stompClient = null;
let sessionId = generateSessionId();
let canvas = null;
let ctx = null;
let cellSize = 20;
let gameStarted = false;

// Генерація унікального ID сесії
function generateSessionId() {
    return 'session-' + Math.random().toString(36).substr(2, 9);
}

// Ініціалізація після завантаження сторінки
document.addEventListener('DOMContentLoaded', function() {
    canvas = document.getElementById('gameCanvas');
    ctx = canvas.getContext('2d');

    const startBtn = document.getElementById('startBtn');
    const restartBtn = document.getElementById('restartBtn');
    const playAgainBtn = document.getElementById('playAgainBtn');

    startBtn.addEventListener('click', startGame);
    restartBtn.addEventListener('click', restartGame);
    playAgainBtn.addEventListener('click', restartGame);

    // Керування клавішами
    document.addEventListener('keydown', handleKeyPress);

    // Підключення до WebSocket
    connect();
});

// Підключення до WebSocket
function connect() {
    const socket = new SockJS('/ws-snake');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        // Підписка на оновлення гри
        stompClient.subscribe('/topic/game/' + sessionId, function(gameState) {
            updateGame(JSON.parse(gameState.body));
        });
    });
}

// Початок гри
function startGame() {
    if (!gameStarted && stompClient) {
        stompClient.send("/app/game/start/" + sessionId, {}, {});
        gameStarted = true;

        document.getElementById('startBtn').style.display = 'none';
        document.getElementById('restartBtn').style.display = 'inline-block';
    }
}

// Перезапуск гри
function restartGame() {
    if (stompClient) {
        document.getElementById('gameOverModal').style.display = 'none';
        stompClient.send("/app/game/restart/" + sessionId, {}, {});
        gameStarted = true;
    }
}

// Обробка натискань клавіш
function handleKeyPress(event) {
    if (!gameStarted) return;

    let direction = null;

    switch(event.key) {
        case 'ArrowUp':
        case 'w':
        case 'W':
            direction = 'UP';
            event.preventDefault();
            break;
        case 'ArrowDown':
        case 's':
        case 'S':
            direction = 'DOWN';
            event.preventDefault();
            break;
        case 'ArrowLeft':
        case 'a':
        case 'A':
            direction = 'LEFT';
            event.preventDefault();
            break;
        case 'ArrowRight':
        case 'd':
        case 'D':
            direction = 'RIGHT';
            event.preventDefault();
            break;
    }

    if (direction && stompClient) {
        stompClient.send("/app/game/direction/" + sessionId, {}, direction);
    }
}

// Оновлення стану гри
function updateGame(gameState) {
    // Очищення canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Малювання сітки
    drawGrid();

    // Малювання змійки
    drawSnake(gameState.snake);

    // Малювання їжі
    drawFood(gameState.food);

    // Оновлення рахунку
    document.getElementById('score').textContent = gameState.score;

    // Перевірка завершення гри
    if (gameState.gameOver) {
        showGameOver(gameState.score);
    }
}

// Малювання сітки
function drawGrid() {
    ctx.strokeStyle = '#ddd';
    ctx.lineWidth = 0.5;

    for (let i = 0; i <= canvas.width; i += cellSize) {
        ctx.beginPath();
        ctx.moveTo(i, 0);
        ctx.lineTo(i, canvas.height);
        ctx.stroke();
    }

    for (let i = 0; i <= canvas.height; i += cellSize) {
        ctx.beginPath();
        ctx.moveTo(0, i);
        ctx.lineTo(canvas.width, i);
        ctx.stroke();
    }
}

// Малювання змійки
function drawSnake(snake) {
    snake.body.forEach((segment, index) => {
        const x = segment.x * cellSize;
        const y = segment.y * cellSize;

        // Голова змійки
        if (index === 0) {
            ctx.fillStyle = '#4caf50';
            ctx.fillRect(x, y, cellSize, cellSize);

            // Очі
            ctx.fillStyle = 'white';
            ctx.beginPath();
            ctx.arc(x + cellSize * 0.3, y + cellSize * 0.3, 2, 0, Math.PI * 2);
            ctx.arc(x + cellSize * 0.7, y + cellSize * 0.3, 2, 0, Math.PI * 2);
            ctx.fill();
        }
        // Тіло змійки
        else {
            ctx.fillStyle = '#8bc34a';
            ctx.fillRect(x + 1, y + 1, cellSize - 2, cellSize - 2);
        }

        // Обводка
        ctx.strokeStyle = '#2e7d32';
        ctx.lineWidth = 1;
        ctx.strokeRect(x, y, cellSize, cellSize);
    });
}

// Малювання їжі
function drawFood(food) {
    const x = food.position.x * cellSize;
    const y = food.position.y * cellSize;

    // Яблуко
    ctx.fillStyle = '#f44336';
    ctx.beginPath();
    ctx.arc(x + cellSize / 2, y + cellSize / 2, cellSize / 2 - 2, 0, Math.PI * 2);
    ctx.fill();

    // Відблиск
    ctx.fillStyle = '#ff8a80';
    ctx.beginPath();
    ctx.arc(x + cellSize * 0.6, y + cellSize * 0.4, cellSize / 6, 0, Math.PI * 2);
    ctx.fill();
}

// Показ екрану завершення гри
function showGameOver(score) {
    gameStarted = false;
    document.getElementById('finalScore').textContent = score;
    document.getElementById('gameOverModal').style.display = 'flex';
}
