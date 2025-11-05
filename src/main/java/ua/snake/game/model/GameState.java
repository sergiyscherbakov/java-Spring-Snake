package ua.snake.game.model;

import lombok.Data;

/**
 * Стан гри
 * @author Сергій Щербаков
 */
@Data
public class GameState {
    private Snake snake;
    private Food food;
    private int score;
    private boolean gameOver;
    private int gridWidth = 30;
    private int gridHeight = 20;

    public GameState() {
        this.snake = new Snake();
        this.food = new Food();
        this.score = 0;
        this.gameOver = false;
        generateFood();
    }

    public void generateFood() {
        int x, y;
        do {
            x = (int) (Math.random() * gridWidth);
            y = (int) (Math.random() * gridHeight);
        } while (snake.getBody().contains(new Point(x, y)));

        food.setPosition(new Point(x, y));
    }

    public void update() {
        if (gameOver) return;

        Point head = snake.getHead();
        boolean willEat = head.getX() + snake.getDirection().getDx() == food.getPosition().getX() &&
                         head.getY() + snake.getDirection().getDy() == food.getPosition().getY();

        snake.move(willEat);

        if (willEat) {
            score += 10;
            generateFood();
        }

        // Перевірка зіткнення зі стінами
        Point newHead = snake.getHead();
        if (newHead.getX() < 0 || newHead.getX() >= gridWidth ||
            newHead.getY() < 0 || newHead.getY() >= gridHeight) {
            gameOver = true;
        }

        // Перевірка зіткнення з собою
        if (snake.checkSelfCollision()) {
            gameOver = true;
        }
    }
}
