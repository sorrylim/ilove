//
//  MultilineTextField.swift
//  IOS
//
//  Created by 김세현 on 2020/07/02.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MultilineTextField : UIViewRepresentable {
    typealias UIViewType = UITextView
    
    var text:String
    
    var isEditing = false
    
    @Binding var content : String
    
    func makeCoordinator() -> MultilineTextField.Coordinator {
                
        return MultilineTextField.Coordinator(parent: self)
    }
    
    func makeUIView(context: UIViewRepresentableContext<MultilineTextField>) -> MultilineTextField.UIViewType {
        let textView=UITextView()
        textView.isEditable=true
        textView.isUserInteractionEnabled=true
        textView.isScrollEnabled=true
        textView.text=self.text
        textView.textColor = .gray
        textView.font = .systemFont(ofSize: 20)
        textView.delegate = context.coordinator
        
        return textView
    }
    
    func updateUIView(_ uiView: MultilineTextField.UIViewType, context: UIViewRepresentableContext<MultilineTextField>) {
        
    }
    
    class Coordinator : NSObject, UITextViewDelegate{
        
        var parent : MultilineTextField
        
        init(parent : MultilineTextField){
            self.parent=parent
        }
        
        func textViewDidChange(_ textView: UITextView) {
            self.parent.content=textView.text
        }
        
        func textViewDidBeginEditing(_ textView: UITextView) {
            if parent.isEditing==false {
                textView.text = ""
            }
            parent.isEditing=true
            textView.textColor = .label
        }
    }
}
