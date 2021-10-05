# 백앤드 개발 지원자 김진수 트리플 과제 제출

안녕하세요!

백앤드 개발 지원자 __김진수__라고 합니다

과제 1,2번 중에 2번을 택해서 과제를 수행하였습니다.

큰 흐름으로 어떻게 과제를 구성했는지에 대해서 간략한 이미지와 설명으로 설명드리겠습니다.

## ERD
![triple-erd](https://user-images.githubusercontent.com/46552466/136020954-2c785467-5631-450d-807f-78545e856213.png)

우선 ERD를 통해 테이블의 관계를 설명드리겠습니다.

### User

최대한 간략한 User의 데이터를 표현하기 위해 생성 날짜와 유저네임만을 가지고 있습니다.
관련된 테이블로 __CityLookUpHistory, Travel table 간의 One to Many 관계__를 가지고 있습니다.

### City

도시 또한 간략하게 도시명만으로 도시의 특성을 나타냈습니다.
관련된 테이블로 __CityLookUpHistory, Travel table 간 One to Many 관계__를 가지고 있습니다.

### Travel

여행은 시작 날짜와 종료 날짜를 추가적인 칼럼으로 가지고 있으며
관련 테이블은 __User, City__와 Many To One 관계를 가지고 있습니다.

### CityLookUpHistory

유저가 한 도시를 조회할 때 마다 하나의 row가 쌓이게 되는 테이블이 CityLookUpHistory입니다
관련 테이블은 __User, City__와 Many To One 관계를 가지고 있습니다.

## Domain

![image](https://user-images.githubusercontent.com/46552466/136019758-0826a1d8-4f8e-45ce-847a-b2833df7a0df.png)

도메인은 테이블와 같은 4개이며 엔티티와 리파지토리를 구성하고 있으며 위의 사진처럼 구성되어 있습니다.

## Controller

![image](https://user-images.githubusercontent.com/46552466/136019828-1e77d455-e8a4-429d-a239-964f93b47487.png)

컨트롤러는 엔티티에 따라 필요한 요청과 응답을 하기 위해 위의 사진처럼 구성되었습니다.
이번 과제에서는 엔티티를 생성하고 저장하는 역할이 공통적으로 들어가있으며 CityController의 경우 과제의 주요 표인트인 City의 List를
응답으로 받을 수 있습니다.

## Exception

![image](https://user-images.githubusercontent.com/46552466/136019879-7f102023-e7cb-42a7-9bf2-5c98e7fd73da.png)

여행의 특성상 __시작 날짜가 종료 날짜보다 빨라야 한다는점, 시작 날짜는 항상 미래의 날짜로 되어야 한다는 점__과 같은 날짜에 대한 Exception 처리가 필요하였습니다.
이에 대한 Exception을 던지도록 하였고 해당 Exception을 Exception Handling Controller에서 처리하도록 구성해보았습니다.

또한 추가적인 커스텀 Exception들을 정의하고 이를 처리하였습니다.

## DTO

![image](https://user-images.githubusercontent.com/46552466/136019924-4686a172-dac8-438f-9171-7b621be1f529.png)

Controller 단에서 요청이나 응답을 할 때, DTO Class를 항상 만들고 이를 이용하여 요청과 응답을 처리하도록 하였습니다.
Entity를 직접 노출시키는 것 보다, DTO를 통해 요청과 응답을 처리하는 것이 변경에 유연하며 안전하다고 생각했기 때문입니다.

## GET CITY LIST

과제의 주요 포인트인 노출되어야 하는 City List를 만들기 위해 
도시리스트 조회 시 정렬 순서에 맞는 쿼리를 각각 만들어서 문제를 해결하였습니다.
도시리스트가 10개가 넘지 않는다면 계속적으로 다음 정렬 순서로 들어가야할 도시들을 조회하고
10개가 될 수 있도록 계속해서 추가하는 방법을 통해 과제를 풀이해보았습니다.

## TEST CASE

![image](https://user-images.githubusercontent.com/46552466/136024373-4b2bfd9e-b611-41f7-a1cb-4b09a294acc2.png)

테스트 케이스는 단위 테스트로 작성하였으며 controller와 repository를 테스트 하였습니다.
총 27개의 테스트 케이스를 작성하였으며 모두 테스트 통과하는 모습을 보이고 있습니다.

감사합니다.







