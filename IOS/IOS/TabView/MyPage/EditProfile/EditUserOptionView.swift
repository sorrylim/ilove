//
//  EditUserOption.swift
//  IOS
//
//  Created by 김세현 on 2020/08/06.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EditUserOptionView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @Binding var userOption : UserOptionModel?
    @State var title : String
    
    @State var gender = ["남성", "여성"]
    @State var purpose = ["결혼", "재혼", "연애"]
    @State var bodyTypes = ["마른","슬림탄탄","보통","글래머","근육질","통통한","뚱뚱한","기타"]
    @State var bloodTypes = ["A", "B", "O", "AB", "몰라요"]
    @State var cities = ["서울", "경기", "인천", "대전", "대구", "부산", "울산", "광주", "강원", "세종", "충북", "충남", "경북", "경남", "전북", "전남", "제주", "해외"]
    @State var jobs = ["회사원", "사무직", "대기업", "외국계 컨설팅관련", "크리에이터", "IT관련", "대기업 무역관련","의사","변호사","공인회계사","사장, 임원","외국계 금융관련","공무원","학생","승무원","교수, 선생님","패션업","연예인, 모델","광고","방송 신문사","웹 관련","아나운서","나레이터 모델","안내원","비서","간호사","보육사","자영업","파일럿","중소기업","금융업","컨설팅업","조리사, 영양사","교육관련","식품관련","제약관련","보험","부동산","건설업","통신","유통","서비스업","미용관련","연예계 관련","여행 관련","결혼관련업","복지관련","약사","스포츠선수","기타"]
    @State var educations = ["대학(2년제)/전문대 졸업","고등학교 졸업","대학(4년제) 졸업","대학원 졸업","기타"]
    @State var holiday = ["토일","평일","때에 따라 달라요"]
    @State var cigarette = 	["비흡연자","흡연자","상대방이 싫다면 끊을 수 있어요","가끔 피워요"]
    @State var alcohol = ["안마셔요","마셔요","가끔 마셔요"]
    @State var religion = ["기독교","불교","천주교","무교","기타"]
    @State var brother = ["첫째","둘째","셋째","기타"]
    @State var country = ["대한민국","일본","중국","미국","캐나다","영국","싱가포르","대만","기타"]
    @State var salary = ["3000만원 미만","3000만원 이상","4000만원 이상","5000만원 이상","6000만원 이상","7000만원 이상","8000만원 이상"]
    @State var asset = ["3000만원 미만","7000만원 미만","1억원 미만","3억원 미만","5억원 미만","10억원 미만","10억원 이상"]
    @State var marriageHistory = ["미혼","이혼 했어요","사별 했어요"]
    @State var children = ["없음","동거중","별거중"]
    @State var marriagePlan = ["바로 하고 싶어요","좋은 사람 있으면 하고 싶어요","상대방에게 맞춰주고 싶어요","모르겠어요","자유롭게 살고싶어요","연애만 하고싶어요"]
    @State var childrenPlan = ["비출산 주의에요","저를 닮은 아이을 낳고싶어요","상대방과 의논하고 싶어요","상대방에게 맞춰주고 싶어요","입양을 하고 싶어요","모르겠어요"]
    @State var wishDate = ["일단 만나보고 싶어요","마음이 맞으면 만나보고 싶어요","아이러브팅에서 충분히 얘기해보고 만나고 싶어요","모르겠어요"]
    @State var dateCost = ["상대가 전부 냈으면 좋겠어요","제가 많이 냈으면 좋겠어요","더치페이 하고싶어요","돈이 많은쪽이 냈으면 좋겠어요","상대방과 의논하고 싶어요","모르겠어요"]
    @State var roomMate = ["혼자 살아요","친구랑 같이 살아요","애완동물이랑 같이 살아요","가족이랑 살아요","기타"]
    @State var language = ["영어","일본어","중국어","힌두어","스페인어","러시아어","프랑스어","아라비아어","포르투갈어","말레이시아어","뱅골어","독일어"]
    @State var interest = ["영화보기","카페가기","코인노래방","편맥하기","수다떨기","맛집찾기","야구보기","축구보기","여행가기","등산하기","춤추기","독서하기"]
    @State var personality = ["엉뚱","활발","도도","친절","애교","털털","성실","착한","4차원","순수","귀여운","다정다감"]
    @State var favoritePerson = ["귀여운","털털한","잘 웃는","유머러스한","다정다감한","엄청 착한","순수한","박력있는","듬직한","마음이 예쁜","욕 안하는","열정적인"]
    
    @State var singleOptionArray : [SingleOptionRow] = []
    @State var multiOptionArray : [MultiOptionRow] = []
    
    @State var alertVisible = false
    
    var body: some View {
        VStack{
            HStack{
                Spacer()
                Image("baseline_rightarrow_grey_24")
                    .resizable()
                    .frame(width:30, height:30)
                    .onTapGesture {
                        if self.singleOptionArray.count>0 {
                            if SingleOptionRow.selected == nil {
                                self.alertVisible=true
                            }
                            else {
                                self.setOption(type: "single")
                            }
                        }
                        else if self.multiOptionArray.count>0 {
                            if MultiOptionRow.selected.count == 0 {
                                self.alertVisible=true
                            }
                            else {
                                self.setOption(type: "multi")
                            }
                        }
                }
            }
            .padding(.bottom,20)
            HStack(spacing: 0){
                if title=="취미" || title=="성격" || title=="내가 좋아하는 사람" {
                    Text("\(title)(을/를) 선택해주세요.(중복선택가능)")
                        .font(.system(size: 25))
                    Spacer()
                }
                else{
                    Text("\(title)(을/를) 선택해주세요.")
                        .font(.system(size: 25))
                    Spacer()
                }
            }
            List{
                if singleOptionArray.count > 0 {
                    ForEach(singleOptionArray, id: \.id){ (option) in
                        option
                    }
                }
                else {
                    ForEach(multiOptionArray, id: \.id){ (option) in
                        option
                    }
                }
            }
            .onAppear(){
                UITableView.appearance().separatorStyle = .none
            }
        }
        .padding()
        .onAppear(){
            
            var id=0
            if self.title == "이용목적" {
                self.purpose.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text: i))
                    id+=1
                }
            }
            else if self.title == "성별" {
                self.gender.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text: i))
                    id+=1
                }
            }
            else if self.title == "키" {
                (150...200).forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:"\(i)cm"))
                    id+=1
                }
            }
            else if self.title == "체형" {
                self.bodyTypes.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "혈액형" {
                self.bloodTypes.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "거주지" {
                self.cities.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "직업" {
                self.jobs.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "학력" {
                self.educations.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "휴일" {
                self.holiday.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "흡연" {
                self.cigarette.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "음주" {
                self.alcohol.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "종교" {
                self.religion.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "형제" {
                self.brother.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "나라" {
                self.country.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "수입" {
                self.salary.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "재산" {
                self.asset.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "결혼여부" {
                self.marriageHistory.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "자녀" {
                self.children.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "결혼계획" {
                self.marriagePlan.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "자녀계획" {
                self.childrenPlan.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "원하는 데이트" {
                self.wishDate.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "데이트 비용" {
                self.dateCost.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "룸메이트" {
                self.roomMate.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "언어" {
                self.language.forEach { (i) in
                    self.singleOptionArray.append(SingleOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "취미" {
                self.interest.forEach { (i) in
                    self.multiOptionArray.append(MultiOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "성격" {
                self.personality.forEach { (i) in
                    self.multiOptionArray.append(MultiOptionRow(id: id, text:i))
                    id+=1
                }
            }
            else if self.title == "내가 좋아하는 사람" {
                self.favoritePerson.forEach { (i) in
                    self.multiOptionArray.append(MultiOptionRow(id: id, text:i))
                    id+=1
                }
            }
        }
        .alert(isPresented: $alertVisible){
            Alert(title: Text("항목을 선택해주세요."))
        }
    }
    
    func setOption(type: String) {
        var userOption : String = ""
        var userOptionData : String = ""
        if type == "single" {
            userOptionData = SingleOptionRow.selected!.text
            if self.title == "이용목적" {
                userOption="user_purpose"
                self.userOption!.user_purpose=userOptionData
            }
            else if self.title == "성별" {
                userOption="user_gender"
                self.userOption!.user_gender = userOptionData
            }
            else if self.title == "키" {
                userOption="user_height"
                self.userOption!.user_height=userOptionData
            }
            else if self.title == "체형" {
                userOption="user_bodytype"
                self.userOption!.user_bodytype = userOptionData
            }
            else if self.title == "혈액형" {
                userOption="user_bloodtype"
                self.userOption!.user_bloodtype = userOptionData
            }
            else if self.title == "거주지" {
                userOption="user_city"
                self.userOption!.user_city = userOptionData
            }
            else if self.title == "직업" {
                userOption="user_job"
                self.userOption!.user_job = userOptionData
            }
            else if self.title == "학력" {
                userOption="user_edcution"
                self.userOption!.user_education = userOptionData
            }
            else if self.title == "휴일" {
                userOption="user_holiday"
                self.userOption!.user_holiday = userOptionData
            }
            else if self.title == "흡연" {
                userOption="user_cigarette"
                self.userOption!.user_cigarette = userOptionData
            }
            else if self.title == "음주" {
                userOption="user_alcohol"
                self.userOption!.user_alcohol = userOptionData
            }
            else if self.title == "종교" {
                userOption="user_religion"
                self.userOption!.user_religion = userOptionData
            }
            else if self.title == "형제" {
                userOption="user_brother"
                self.userOption!.user_brother = userOptionData
            }
            else if self.title == "나라" {
                userOption="user_country"
                self.userOption!.user_country = userOptionData
            }
            else if self.title == "수입" {
                userOption="user_salary"
                self.userOption!.user_salary = userOptionData
            }
            else if self.title == "재산" {
                userOption="user_asset"
                self.userOption!.user_asset = userOptionData
            }
            else if self.title == "결혼여부" {
                userOption="user_marriagehistory"
                self.userOption!.user_marriagehistory = userOptionData
            }
            else if self.title == "자녀" {
                userOption="user_children"
                self.userOption!.user_children = userOptionData
            }
            else if self.title == "결혼계획" {
                userOption="user_marriageplan"
                self.userOption!.user_marriageplan = userOptionData
            }
            else if self.title == "자녀계획" {
                userOption="user_childrenplan"
                self.userOption!.user_childrenplan = userOptionData
            }
            else if self.title == "원하는 데이트" {
                userOption="user_wishdata"
                self.userOption!.user_wishdate = userOptionData
            }
            else if self.title == "데이트 비용" {
                userOption="user_datecost"
                self.userOption!.user_datecost = userOptionData
            }
            else if self.title == "룸메이트" {
                userOption="user_roommate"
                self.userOption!.user_roommate = userOptionData
            }
            else if self.title == "언어" {
                userOption="user_language"
                self.userOption!.user_language = userOptionData
            }
        }
        else {
            MultiOptionRow.selected.forEach { (option) in
                userOptionData.append("\(option.text),")
            }
            
            userOptionData.removeLast()
            if self.title == "취미" {
                userOption="user_interest"
                self.userOption!.user_interest = userOptionData
            }
            else if self.title == "성격" {
                userOption="user_personality"
                self.userOption!.user_interest = userOptionData
            }
            else if self.title == "내가 좋아하는 사람" {
                userOption="user_favoriteperson"
                self.userOption!.user_interest = userOptionData
            }
        }
        
        if userOption=="user_city" || userOption=="user_purpose" || userOption=="user_gender" {
            HttpService.shared.updateOptionCityReq(userId: UserInfo.shared.ID, userOption: userOption, userOptionData: userOptionData){
                self.presentationMode.wrappedValue.dismiss()
            }
        }
        else {
            HttpService.shared.updateOptionReq(userId: UserInfo.shared.ID, userOption: userOption, userOptionData: userOptionData){
                self.presentationMode.wrappedValue.dismiss()
            }
        }
    }
}

struct SingleOptionRow : View{
    static var selected : SingleOptionRow?
    
    @State var id : Int
    @State var text : String
    @State var didTap = false
    
    var body: some View{
        HStack{
            Text(text)
                .font(.system(size: 25))
                .foregroundColor(didTap ? Color(red: 255/255, green: 160/255, blue: 0/255) : Color.gray)
                .onTapGesture {
                    self.didTap.toggle()
                    if self.didTap == true {
                        if SingleOptionRow.selected != nil {
                            SingleOptionRow.selected!.didTap = false
                        }
                        SingleOptionRow.selected=self
                    }
                    else {
                        SingleOptionRow.selected=nil
                    }
            }
            Spacer()
        }
    }
}

struct MultiOptionRow : View{
    static var selected : [MultiOptionRow] = []
    
    @State var id : Int
    @State var text : String
    @State var didTap = false
    
    var body: some View{
        HStack{
            Text(text)
                .font(.system(size: 25))
                .foregroundColor(didTap ? Color(red: 255/255, green: 160/255, blue: 0/255) : Color.gray)
                .onTapGesture {
                    self.didTap.toggle()
                    if self.didTap == true {
                        MultiOptionRow.selected.append(self)
                    }
                    else {
                        for i in 0..<MultiOptionRow.selected.count {
                            if MultiOptionRow.selected[i].id == self.id {
                                MultiOptionRow.selected.remove(at: i)
                            }
                        }
                    }
            }
            Spacer()
        }
    }
}
