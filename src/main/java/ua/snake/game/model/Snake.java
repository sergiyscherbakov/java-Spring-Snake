package ua.snake.game.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель змійки
 * @author Сергій Щербаков
 */
@Data
public class Snake {
    private List<Point> body;
    private Direction direction;

    public Snake() {
        this.body = new ArrayList<>();
        // Початкова позиція змійки
        this.body.add(new Point(10, 10));
        this.body.add(new Point(9, 10));
        this.body.add(new Point(8, 10));
        this.direction = Direction.RIGHT;
    }

    public Point getHead() {
        return body.get(0);
    }

    public void move(boolean grow) {
        Point head = getHead();
        Point newHead = new Point(
            head.getX() + direction.getDx(),
            head.getY() + direction.getDy()
        );

        body.add(0, newHead);

        if (!grow) {
            body.remove(body.size() - 1);
        }
    }

    public boolean checkSelfCollision() {
        Point head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void changeDirection(Direction newDirection) {
        if (newDirection != direction.opposite()) {
            this.direction = newDirection;
        }
    }
}
