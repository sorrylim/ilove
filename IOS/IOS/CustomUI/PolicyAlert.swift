//
//  PolicyAlert.swift
//  IOS
//
//  Created by 김세현 on 2020/08/11.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct PolicyAlert: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @Binding var showing : Bool
    @State var certification1Visible = false
    
    var body: some View {
        ZStack{
            VStack{
                ScrollView{
                Text("\n개인정보 취급방침\n\n서비스 '아이러브팅; 제공 회사(이사 '회사')는 통신비밀보호법, 전기통신사업법, 정보통신망 이용촉진 및 정보보호 등에 관한 법률 등 정보통신 서비스 제공자가 준수하여야 할 관련 법령상의 개인정보 보호 규정을 준수하며, 관련 법령에 의거한 개인정보 취급방침을 정하여 이용자 권익 보호에 최선을 다하고 있습니다. 회사의 개인정보 취급방침은 다음과 같은 내뇽을 담고 있습니다.\n\n제1조 취지 및 수집 근거\n제2조 개인정보 수집 항목 및 방법\n제3조 개인정보 수집 및 이용\n제4조 개인정보 이용 및 보유\n제5조 개인정보의 파기\n제6조 개인정보의 취급 위탁\n제7조 개인정보 취급 면책 사항\n제8조 개인정보 제공자 권리 보전\n제9조 개인정보 관리 책임 이행 및 담당자 규정\n\n\n제1조 목적 및 근거\n회사가 개발하여 제공하는 웹, 모바일 플랫폼을 포함한 기타 서비스(이하 '서비스')를 제공하기 위해서는 서비스 가입회원(이하 '회원')의 동의 하에 제공된 개인정보가 필요합니다. 개인 정보 수집의 근거는 서비스 가입 시 제공하는 해당 문서 내 항목에 동의하는 것으로 합니다. 기본 서비스 제공을 위해 필요한 기초 정보는 회원이 서비스 가입을 위해 제공된 기입 양식에 자발적으로 작성한 내용을 중심으로 하며, 필요한 경우 해당 문서에 동의한 내용을 토대로 정보를 추가 수집할 수 있습니다.\n\n제2조 개인정보 수집 항목 및 방법\n1. 개인정보 수집 항목\n회원의 개인 정보 수집 항목은 이용 목적 등과 같은 조건 하에 구분되며, 정보 수집동의를 통한 합법적 근거로 정보를 수집합니다. 회사는 필요할 경우 별도 양식 활용 및 동의 절차를 거쳐 회원의 개인정보를 수집할 수 있습니다. 목적 별 수집 항목은 다음과 같습니다.\n가. 서비스 이용 필수항목 : ID")
                    .font(.system(size: 10))
                    .foregroundColor(.gray)
                    
                }
                .padding(.top,10)
                .padding(.horizontal,10)
                .background(Color.white)
            }
            .padding(.bottom,UIScreen.main.bounds.height*0.07)
            VStack{
                Spacer()
                NavigationLink(destination: Certification1View()){
                    Text("동의합니다.")
                        .font(.system(size: 15))
                        .foregroundColor(.white)
                    
                }
                .frame(width: UIScreen.main.bounds.width*0.8, height: UIScreen.main.bounds.height*0.07)
                .background(Color(red: 255/255, green: 160/255, blue: 0/255))
            }
        }
        .edgesIgnoringSafeArea(.top)
        .padding(.bottom,50)
        .frame(width: UIScreen.main.bounds.width*0.8, height: UIScreen.main.bounds.height*0.8)
    }
}
