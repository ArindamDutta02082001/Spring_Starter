package com.example.project.onlinelibrarywithsecurity.responseObject;

import com.example.project.onlinelibrarywithsecurity.models.libraryModels.Student;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class studentResponse implements Serializable {


    private int id;

    private String name;

    private String contact;

    private Date createdOn;

    private Date updatedOn;

    public studentResponse(Student student)
    {
        this.name = student.getName();
        this.contact=student.getContact();
        this.id  =student.getId();
        this.createdOn = student.getCreatedOn();
        this.updatedOn = student.getUpdatedOn();

    }

}
