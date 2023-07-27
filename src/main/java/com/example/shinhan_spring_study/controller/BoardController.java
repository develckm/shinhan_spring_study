package com.example.shinhan_spring_study.controller;

import com.example.shinhan_spring_study.entity.Boards;
import com.example.shinhan_spring_study.repository.BoardRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller //동적리소스를 생성하는 컨틀롤러(==서블릿)
@RequestMapping("/board")
@Log4j2 //log 를 작성가능 (파일로 저장)
public class BoardController {
    //@Autowired
    //private DataSource dataSource;
    //IoC : 제어의 역전 (디자인패턴,구현체,Spring Container)
    // 프로그램에서 객체생성 프로그래머에 의해서
    // -> Spring Container(Factory)에서 생성한 객체를 주입(DI)
    //DI 으로 객체 주입을 받는 방법 (인터페이스를 타입으로 사용)
    //생성자에 초기화 조건으로 추가(권장),@AutoWired(비권장)
    private BoardRepository boardRepository;
    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    //동적리소스인 함수의 매개변수로 사용 하고 싶은 타입의 객체를 사용하면 제공(==생성자 di)
    //Model,ModelAndView : 렌더링할 view 에 객체를 전달하는 타입
    //기본형 파라미터인데 존재하지 않을 수도 있는 파라미터는 defaultValue 작성
    @GetMapping("/list.do")
    public void list(
            Model model,
            @RequestParam(defaultValue = "1") int page
    ){
        List<Boards> boardList=boardRepository.findAll();
        model.addAttribute("boards",boardList);
//        try {
//            Connection conn =dataSource.getConnection();
//            PreparedStatement pstmt =conn.prepareStatement("SELECT * FROM BOARDS");
//            ResultSet rs=pstmt.executeQuery();
//            while(rs.next()){
//                System.out.println(rs.getString("title"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        //return "/board/list"; // /templates/board/list.html 을 렌더후 응답
    }
    //@RequestMapping(method = RequestMethod.GET,value = "/detail.do")
    @GetMapping("/detail.do")
    public String detail(
            @RequestParam(required = true) int bId,
            Model model
        ){
        Optional<Boards> boardOpt=boardRepository.findById(bId);
        if(boardOpt.isPresent()){
            model.addAttribute("b",boardOpt.get());
            return "board/detail";
        }else{
            //302 서버 내에서 다른 동적 리소스 요청
            return "redirect:/board/list.do";
        }
    }
    //ModelAttribute 파라미터를 get 함수가 정의된 필드가 파싱
    //특정 파라미터를 강제하고 싶다면(400) 생성자에 필드를 초기화 규칙으로 추가
    //Entity 보다 dto를 따로 만들어서 사용하는 것을 권장
    @PostMapping("/modify.do")
    public String modify(
            @ModelAttribute Boards boards
        ){
        //양식을 제출받는 동적리소스 == action page(처리하는 페이지)
        //해당 동록 리소스가 응답하지 않는다.(다른페이지로 이동 302)
        //수정이 성공 -> list(o) or detail or 수정성공페이지
        //수정이 실패 -> 수정페이지(detail o) 실패 안내!(alert)
        boolean modify=false;
        log.info(boards);
        //dml 호출시 try 사용 권장
        try {
            //jap 영속성관리 파라미터로 보낸 값이 db로 저장된 것과 같다면
            // 수정을 하지 않는다.
            Boards b=boardRepository.save(boards);
            if (b!=null)modify=true;

        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(modify){
            return "redirect:/board/list.do";
        }else{
            return "redirect:/board/detail.do?bId="+boards.getBId();
        }

    }


}
