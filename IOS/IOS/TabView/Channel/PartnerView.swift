//
//  PartnerView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/24.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct PartnerView : View {
    
    @State var partnerList:[PartnerModel]=[]
    
    var title:String
    
    var body: some View{
        List{
            ForEach(partnerList, id: \.user_id){partner in
                PartnerRow(partner: partner)
            }
        }
        .navigationBarTitle("\(self.title)",displayMode: .inline)
        .onAppear{
            switch self.title {
            case "내 프로필을 확인한 사람":
            case "내 스토리를 확인한 사람":
            case "내가 좋아요를 보낸 이성":
                HttpService.shared.getPartnerReq(userId: "hyeha", expressionType: "like", sendType:"send") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "나에게 좋아요를 보낸 이성":
                HttpService.shared.getPartnerReq(userId: "hyeha", expressionType: "like", sendType:"receive") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "서로 좋아요가 연결된 이성":
            case "내가 만나고 싶은 그대":
                HttpService.shared.getPartnerReq(userId: "hyeha", expressionType: "meet", sendType:"send") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "나를 만나고 싶어하는 그대":
                HttpService.shared.getPartnerReq(userId: "hyeha", expressionType: "meet", sendType:"receive") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "서로 연락처를 주고받은 그대":
            default:
                print("default")
            }
        }
    }
}

struct PartnerRow : View{
    
    var partner : PartnerModel
    
    var body: some View {
        VStack{
            Text(partner.expression_date)
                .font(.system(size:10))
            HStack(spacing: 20){
                Image(systemName: "pencil")
                    .frame(width: 50, height: 50)
                VStack(alignment: .leading){
                    Text(partner.user_nickname)
                        .font(.system(size:20,weight:.bold))
                    Spacer()
                    Text("\(partner.user_birthday), \(partner.user_city)")
                        .font(.system(size:15))
                }
                Spacer()
                Image(systemName: "heart.fill")
                    .foregroundColor(.red)
            }
            .padding(10)
        }
    }
}
