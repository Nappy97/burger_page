package com.nappy.burger.service;

import com.nappy.burger.domain.board.Board;
import com.nappy.burger.domain.board.BoardRepository;
import com.nappy.burger.domain.boardReply.boardReply;
import com.nappy.burger.domain.boardReply.boardReplyRepository;
import com.nappy.burger.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final boardReplyRepository boardReplyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void replySave(Long boardId, boardReply boardReply, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 boardId가 없습니다. id=" + boardId));

        boardReply.save(board, user);

        boardReplyRepository.save(boardReply);
    }

    @Transactional
    public void replyDelete(Long replyId) {
        boardReplyRepository.deleteById(replyId);
    }
}
