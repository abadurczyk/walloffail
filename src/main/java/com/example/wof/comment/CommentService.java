package com.example.wof.comment;

import com.example.wof.fails.FailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;


    public List<CommentDto> getComment(Integer failId) {
        List<CommentEntity> allByFailId = commentRepository.findAllByFailId(failId);
        return allByFailId.stream().map(CommentMapper.INSTANCE::commentEntityToComment).toList();

    }

    public void saveComment(CommentDto comment) {
        Optional<CommentEntity> byId = commentRepository.findById(comment.getFailId());
        if (byId.isEmpty()) {
            // throw Exception
        }

        commentRepository.save(CommentMapper.INSTANCE.commentDtoToCommentEntity(comment));
    }

    public void deleteComment(Integer commentId) {
        commentRepository.findById(commentId).ifPresent(c -> commentRepository.deleteById(commentId));
    }
}
