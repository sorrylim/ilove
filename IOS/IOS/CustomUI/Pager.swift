//
//  Pager.swift
//  IOS
//
//  Created by 김세현 on 2020/07/23.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct Pager : UIViewRepresentable{
    typealias UIViewType = UIScrollView
    
    var userId : String
    
    var width : CGFloat
    var height : CGFloat
    
    
    @Binding var page : Int
    @State var imageList : [UIImage]
    
    func makeUIView(context: UIViewRepresentableContext<Pager>) -> UIScrollView {
        
        let view = UIScrollView()
        let total = width * CGFloat(imageList.count)
        
        view.isPagingEnabled = true
        view.contentSize = CGSize(width: total, height: 1.0)
        view.bounces = true
        view.showsVerticalScrollIndicator = false
        view.showsHorizontalScrollIndicator = false
        
        let view1 = UIHostingController(rootView: ProfileImageList(userId: userId))
        view1.view.frame = CGRect(x: 0,y: 0, width: total,height: self.height)
        view1.view.backgroundColor = .clear
        
        view.addSubview(view1.view)
        
        return view
    }
    
    func updateUIView(_ uiView: UIScrollView, context: UIViewRepresentableContext<Pager>) {
        
        
        
    }
}
