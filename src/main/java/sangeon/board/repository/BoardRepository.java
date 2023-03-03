package sangeon.board.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sangeon.board.entity.board.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
