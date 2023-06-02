package controller;

import dto.request.service.CalculateRequest;
import dto.request.view.input.ItemInputRequest;
import dto.request.view.output.ResultViewRequest;
import dto.response.input.BackpackInputResponse;
import dto.response.input.ItemInputResponse;
import dto.response.input.NumberOfItemResponse;
import dto.response.service.CalculateResponse;
import service.BackpackCalculateService;
import service.validation.ItemDuplicateValidation;
import view.input.BackpackInputView;
import view.input.ItemInputView;
import view.input.NumberOfItemInputView;
import view.output.ResultView;

public class BackpackController {
    //View
    private final ItemInputView itemInputView = new ItemInputView();                                //Item 을 입력 받는 View
    private final NumberOfItemInputView numberOfItemInputView = new NumberOfItemInputView();        //처음 아이템 입력을 띄워주는 뷰
    private final BackpackInputView backpackInputView = new BackpackInputView();                    //배낭의 크기를 입력 받는 View
    private final ResultView resultView = new ResultView();                                         //결과를 보여주는 View

    //Service
    private final BackpackCalculateService calculateService = new BackpackCalculateService();       //입력된 것을 바탕으로 계산하는 Service
    private final ItemDuplicateValidation itemDuplicateValidation = new ItemDuplicateValidation();  //Item 이 중복되는지 검사하는 Service

    public void calculateStart() {
        //input
        NumberOfItemResponse numberOfItemResponse =
                numberOfItemInputView.getNumberOfItem();                                            //Item 몇 개를 입력 받아야 하는지 입력 받는다
        ItemInputResponse itemInputResponse =
                itemInputView.input(ItemInputRequest.from(numberOfItemResponse));                   //Item 의 정보를 입력 받는다
        BackpackInputResponse backpackInputResponse =
                backpackInputView.input();                                                          //배낭의 정보를 입력 받는다

        //service
        itemDuplicateValidation.check(itemInputResponse);                                           //입력된 정보를 바탕으로 중복이 있는지 검사한다
        CalculateResponse calculateResponse =                                                       //계산을 한 후 Dto 로 매핑한다
                calculateService.calculate(
                        CalculateRequest.from(itemInputResponse, backpackInputResponse)
                );

        //output
        resultView.showResult(ResultViewRequest.from(calculateResponse));                           //계산 결과를 프린트 한다.
    }
}
