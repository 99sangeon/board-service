package sangeon.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sangeon.board.entity.board.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByParentIsNull();
    List<Comment> findByParent(Comment parent);

}
