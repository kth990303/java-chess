package chess.domain.piece;

import chess.domain.Position;
import java.util.Objects;

public class Pawn implements Piece {

    private static final int PAWN_DEFAULT_MOVE_DISTANCE = 1;
    private static final int PAWN_FIRST_MOVE_DISTANCE = 2;
    private static final int PAWN_DIAGONAL_MOVE_DISTANCE = 2;

    private Position position;

    public Pawn(Position position) {
        this.position = position;
    }

    @Override
    public Position move(final Position currentPosition, final Position destinationPosition) {
        if (!currentPosition.isMoveForward(destinationPosition)) {
            throw new IllegalArgumentException("폰은 캡쳐할 수 있는 상대말이 없는 경우, 앞으로만 이동할 수 있습니다.");
        }
        if (!position.isFirstTurnOfPawn()) {
            validateDefaultMoveDistance(currentPosition.calculateDistance(destinationPosition));
        }
        validateFirstTurnMove(currentPosition, destinationPosition);
        return position = destinationPosition;
    }

    private void validateFirstTurnMove(final Position currentPosition, final Position destinationPosition) {
        final int distance = currentPosition.calculateDistance(destinationPosition);
        if (distance != PAWN_DEFAULT_MOVE_DISTANCE && distance != PAWN_FIRST_MOVE_DISTANCE) {
            throw new IllegalArgumentException("폰은 첫번째 턴에는 1칸 또는 2칸만 이동할 수 있습니다.");
        }
    }

    private void validateDefaultMoveDistance(final int distance) {
        if (distance != PAWN_DEFAULT_MOVE_DISTANCE) {
            throw new IllegalArgumentException("폰은 앞으로 1칸만 이동할 수 있습니다.");
        }
    }

    @Override
    public Position capture(final Position currentPosition, final Position destinationPosition) {
        final boolean isMoveDiagonal = currentPosition.isMoveDiagonal(destinationPosition);
        final int moveDistance = currentPosition.calculateDistance(destinationPosition);

        if (!isMoveDiagonal || moveDistance != PAWN_DIAGONAL_MOVE_DISTANCE) {
            throw new IllegalArgumentException("폰은 상대 말이 존재할 경우만 대각선으로 1칸만 이동할 수 있습니다.");
        }
        return position = destinationPosition;
    }

    @Override
    public boolean exist(final Position checkingPosition) {
        return position.equals(checkingPosition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pawn pawn = (Pawn) o;
        return Objects.equals(position, pawn.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
