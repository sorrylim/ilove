//
//  BlurView.swift
//  IOS
//
//  Created by 김세현 on 2020/08/05.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import UIKit

struct BlurView: UIViewRepresentable {
    
    var effect : UIBlurEffect.Style
    
    func makeUIView(context: UIViewRepresentableContext<BlurView>) -> UIVisualEffectView {
        
        let blurEffect = UIBlurEffect(style: effect)
        let blurView = UIVisualEffectView(effect: blurEffect)
        
        return blurView
    }
    
    func updateUIView(_ uiView: UIVisualEffectView, context: UIViewRepresentableContext<BlurView>) {
        
    }
}
