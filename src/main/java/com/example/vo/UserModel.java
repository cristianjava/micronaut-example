package com.example.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class UserModel {

    private List<DataModel> dataModelList;
}
