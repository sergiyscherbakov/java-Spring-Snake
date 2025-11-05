package ua.snake.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Їжа для змійки
 * @author Сергій Щербаков
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    private Point position;
}
