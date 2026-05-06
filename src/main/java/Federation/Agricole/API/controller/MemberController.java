package Federation.Agricole.API.controller;

import Federation.Agricole.API.controller.dto.CreateMember;
import Federation.Agricole.API.controller.mapper.MemberDtoMapper;
import Federation.Agricole.API.entity.Member;
import Federation.Agricole.API.exception.BadRequestException;
import Federation.Agricole.API.exception.NotFoundException;
import Federation.Agricole.API.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberDtoMapper memberDtoMapper;

    @PostMapping("/members")
    public ResponseEntity<?> createMembers(@RequestBody List<CreateMember> createMemberDtos) {
        try {
            List<Member> convertedCreateMembers = createMemberDtos.stream()
                    .map(memberDtoMapper::mapToEntity)
                    .toList();

            List<Member> savedMembers = memberService.addNewMembers(convertedCreateMembers);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(savedMembers.stream()
                            .map(memberDtoMapper::mapToDto)
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
