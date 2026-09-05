package com.mrmichaelk.money_man.core.profile.create;

import lombok.*;

@Builder 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class CreateProfileResponse {
    private int id;

    private String name;
}
