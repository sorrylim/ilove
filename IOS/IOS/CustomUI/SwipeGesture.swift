//
//  SwipeGesture.swift
//  IOS
//
//  Created by 김세현 on 2020/07/23.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SwipeGesture : UIViewRepresentable {
    
    @Binding var imageList : [UIImage]
    
    func makeCoordinator() -> SwipeGesture.Coordinator {
        return SwipeGesture.Coordinator(totalPage: imageList.count)
    }
    
    func makeUIView(context: UIViewRepresentableContext<SwipeGesture>) -> UIView {
        
        let view = UIView()
        view.backgroundColor = .clear
        
        let left = UISwipeGestureRecognizer(target: context.coordinator, action: #selector(context.coordinator.left))
        left.direction = .left
        
        let right = UISwipeGestureRecognizer(target: context.coordinator, action: #selector(context.coordinator.right))
        right.direction = .right
        
        view.addGestureRecognizer(left)
        view.addGestureRecognizer(right)
        
        return view
    }
    
    func updateUIView(_ uiView: UIView, context: UIViewRepresentableContext<SwipeGesture>) {
        
    }
    
    class Coordinator : NSObject {
        
        var current = 0
        var totalPage : Int
        
        init(totalPage : Int) {
            self.totalPage=totalPage
        }
        
        @objc func left(){
            print("left")
            if current > 0 {
                current-=1
            }
        }
        
        @objc func right(){
            if current < totalPage-1{
                current+=1
            }
        }
    }
}
