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
    @State var type=""
    
    
    var title:String
    
    var body: some View{
        List{
            ForEach(partnerList, id: \.user_id){partner in
                PartnerRow(partner: partner)
            }
            .onDelete(perform : delete)
        }
        .navigationBarTitle("\(self.title)",displayMode: .inline)
        .onAppear{
            switch self.title {
            case "내 프로필을 확인한 사람":
                self.type="profile"
                HttpService.shared.getVisitUserReq(userId: "ksh", visitType: "profile") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "내 스토리를 확인한 사람":
                self.type="story"
                HttpService.shared.getVisitUserReq(userId: "ksh", visitType: "story") { ( partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "내가 좋아요를 보낸 이성":
                self.type="like"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "like", sendType:"send") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "나에게 좋아요를 보낸 이성":
                self.type="like"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "like", sendType:"receive") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "서로 좋아요가 연결된 이성":
                self.type="like"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "like", sendType: "each1") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                    HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "like", sendType: "each2") { (partnerModelArray2) -> Void in
                        self.partnerList+=partnerModelArray2
                    }
                }
            case "내가 만나고 싶은 그대":
                self.type="meet"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "meet", sendType:"send") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "나를 만나고 싶어하는 그대":
                self.type="meet"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "meet", sendType:"receive") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                }
            case "서로 연락처를 주고받은 그대":
                self.type="meet"
                HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "meet", sendType: "each1") { (partnerModelArray) -> Void in
                    self.partnerList=partnerModelArray
                    HttpService.shared.getPartnerReq(userId: "ksh", expressionType: "meet", sendType: "each2") { (partnerModelArray2) -> Void in
                        self.partnerList+=partnerModelArray2
                    }
                }
            default:
                print("default")
            }
        }
    }
    
    func delete(at offsets: IndexSet){
        if type=="story" || type=="profile" {
            return;
        }
        if let first = offsets.first {
            HttpService.shared.deleteExpressionReq(userId: "ksh", partnerId: partnerList[first].user_id, expressionType: "like") { (resultModel) -> Void in
                if resultModel.result=="success" {
                    self.partnerList.remove(at: first)
                }
            }
        }
    }
}

struct PartnerRow : View{
    
    @State var partner : PartnerModel
    
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
            }
            .padding(10)
        }
    }
}
