package com.trianglechoke.codesparring.room.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomMemberDTO {
    private Long roomNo;
    private Long memberNo;
    private String memberName;
    private Integer memberProfileImg;
    private Long memberLevel;
    private String memberTier;
    private Integer hostStatus;
}
