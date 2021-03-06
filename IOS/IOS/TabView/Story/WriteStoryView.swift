//
//  WriteStoryView.swift
//  IOS
//
//  Created by 김세현 on 2020/06/19.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct WriteStoryView : View{
    
    @Environment(\.presentationMode) var presentationMode
    
    @State var content=""
    
    @State var image : Image?
    
    
    @State var imagePickerVisiable=false
    @State var inputImage : UIImage?
    @State var imageUrl : URL?
    
    @State var value : CGFloat=0
    var body: some View{
            VStack{
                HStack{
                    Spacer()
                    Text("스토리 작성")
                    Spacer()
                    Button(action: {
                        self.writeStory()
                    }){
                        Text("등록")
                    }
                }
                MultilineTextField(text:"당신에 대해 이야기 해주세요",content: $content)
                HStack{
                    Spacer()
                    if image != nil{
                        image?
                            .resizable()
                            .scaledToFit()
                    }
                    Spacer()
                }
                .frame(height: 200)
                
                Button(action: {
                    self.imagePickerVisiable=true
                }){
                    Text("사진 추가")
                }
                .offset(y: -self.value)
                .onAppear(){
                    NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillShowNotification, object: nil, queue: .main) { noti in
                        let value = noti.userInfo![UIResponder.keyboardFrameEndUserInfoKey] as! CGRect
                        let height = value.height
                        
                        self.value=height
                    }
                    
                    NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillHideNotification, object: nil, queue: .main){ noti in
                        self.value=0
                    }
                }
            }
            .padding()
            .sheet(isPresented: $imagePickerVisiable, onDismiss: loadImage){
                ImagePicker(image: self.$inputImage, imageUrl: self.$imageUrl)
            }
            .onTapGesture {
                UIApplication.shared.endEditing()
            }
        
    }
    
    func loadImage(){
        guard let inputImage=inputImage else {
            return
        }
        image=Image(uiImage: inputImage)
    }
    
    func writeStory(){
        if inputImage != nil{
            let now = Date()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            let date=dateFormatter.string(from: now)
            
            HttpService.shared.insertStoryReq(userId: UserInfo.shared.ID,imageUrl: imageUrl!,imageUsage: "story",imageContent: content,imageDate: date,uiImage: inputImage!)
            
            presentationMode.wrappedValue.dismiss()
        }
    }
}
