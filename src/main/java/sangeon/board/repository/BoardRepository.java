package sangeon.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sangeon.board.entity.board.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
