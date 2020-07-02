package com.ilove.ilove.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : PSAppCompatActivity() {

    var userOptionList =  ArrayList<UserItem.UserOption>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        image_editmain.setClipToOutline(true)
        image_editsub1.setClipToOutline(true)
        image_editsub2.setClipToOutline(true)
        image_editsub3.setClipToOutline(true)


        toolbarBinding(toolbar_editprofile, "프로필 편집", true)
    }

    fun bodyType() {
        userOptionList = arrayListOf(UserItem.UserOption("마른", 0), UserItem.UserOption("슬림탄탄", 0), UserItem.UserOption("보통", 0), UserItem.UserOption("글래머", 0), UserItem.UserOption("근육질", 0), UserItem.UserOption("통통한", 0), UserItem.UserOption("뚱뚱한", 0), UserItem.UserOption("기타", 0))
    }

    fun bloodType() {
        userOptionList = arrayListOf(UserItem.UserOption("A", 0), UserItem.UserOption("B", 0), UserItem.UserOption("O", 0),UserItem.UserOption("AB", 0), UserItem.UserOption("몰라요", 0))
    }

    fun regidence() {
        userOptionList = arrayListOf(UserItem.UserOption("서울", 0), UserItem.UserOption("경기", 0), UserItem.UserOption("인천", 0), UserItem.UserOption("대전", 0), UserItem.UserOption("대구", 0), UserItem.UserOption("부산", 0), UserItem.UserOption("울산", 0), UserItem.UserOption("광주", 0), UserItem.UserOption("강원", 0), UserItem.UserOption("세종", 0), UserItem.UserOption("충북", 0),
            UserItem.UserOption("충남", 0), UserItem.UserOption("경북", 0), UserItem.UserOption("전남", 0), UserItem.UserOption("제주", 0), UserItem.UserOption("해외", 0))
    }

    fun job() {
        userOptionList = arrayListOf(UserItem.UserOption("회사원", 0), UserItem.UserOption("사무직", 0), UserItem.UserOption("대기업", 0), UserItem.UserOption("외국계 컨설팅관련", 0), UserItem.UserOption("크리에이터", 0), UserItem.UserOption("IT관련업", 0), UserItem.UserOption("대기업 무역관련", 0), UserItem.UserOption("의사", 0),
            UserItem.UserOption("변호사", 0), UserItem.UserOption("공인회계사", 0), UserItem.UserOption("사장, 임원", 0), UserItem.UserOption("외국계 금융관련", 0), UserItem.UserOption("공무원", 0), UserItem.UserOption("학생", 0), UserItem.UserOption("승무원", 0), UserItem.UserOption("교수, 선생님", 0), UserItem.UserOption("패션업", 0),
            UserItem.UserOption("연예인, 모델", 0), UserItem.UserOption("광고", 0), UserItem.UserOption("방송, 신문사", 0), UserItem.UserOption("WEB관련", 0), UserItem.UserOption("아나운서", 0), UserItem.UserOption("나레이터 모델", 0), UserItem.UserOption("안내원", 0), UserItem.UserOption("비서", 0), UserItem.UserOption("간호사", 0), UserItem.UserOption("보육사", 0),
            UserItem.UserOption("자영업", 0), UserItem.UserOption("파일럿", 0), UserItem.UserOption("중소기업", 0), UserItem.UserOption("금융업", 0), UserItem.UserOption("컨설팅업", 0), UserItem.UserOption("조리사, 영양사", 0), UserItem.UserOption("교육관련", 0), UserItem.UserOption("식품관련", 0), UserItem.UserOption("제약관련", 0), UserItem.UserOption("보험", 0), UserItem.UserOption("부동산", 0),
            UserItem.UserOption("건설업", 0), UserItem.UserOption("통신", 0), UserItem.UserOption("유통", 0), UserItem.UserOption("서비스업", 0), UserItem.UserOption("미용관련", 0), UserItem.UserOption("연예게 관련", 0), UserItem.UserOption("여행관련", 0), UserItem.UserOption("결혼관련업", 0), UserItem.UserOption("복지관련", 0), UserItem.UserOption("약사", 0),
            UserItem.UserOption("스포츠선수", 0), UserItem.UserOption("기타", 0))
    }

    fun education() {
        userOptionList = arrayListOf(UserItem.UserOption("대학(2년제)/전문대 졸업", 0), UserItem.UserOption("고등학교 졸업", 0), UserItem.UserOption("대학교(4년제)졸업", 0), UserItem.UserOption("대학원 졸업", 0), UserItem.UserOption("기타", 0))
    }

    fun holiday() {
        userOptionList = arrayListOf(UserItem.UserOption("토일", 0), UserItem.UserOption("평일", 0), UserItem.UserOption("때에 따라 달라요", 0))
    }

    fun cigarette() {
        userOptionList = arrayListOf(UserItem.UserOption("비흡연자", 0), UserItem.UserOption("흡연자", 0), UserItem.UserOption("상대방이 싫다면 끊을 수 있어요", 0), UserItem.UserOption("가끔 피워요", 0))
    }

    fun alcohol() {
        userOptionList = arrayListOf(UserItem.UserOption("안마셔요", 0), UserItem.UserOption("마셔요", 0), UserItem.UserOption("가끔마셔요", 0))
    }

    fun religion() {
        userOptionList = arrayListOf(UserItem.UserOption("기독교", 0), UserItem.UserOption("불교", 0), UserItem.UserOption("천주교", 0), UserItem.UserOption("무교", 0), UserItem.UserOption("기타", 0))
    }

    fun brother() {
        userOptionList = arrayListOf(UserItem.UserOption("첫째", 0), UserItem.UserOption("둘째", 0), UserItem.UserOption("셋째", 0), UserItem.UserOption("기타", 0))
    }

    fun country() {
        userOptionList = arrayListOf(UserItem.UserOption("대한민국", 0), UserItem.UserOption("일본", 0), UserItem.UserOption("중국", 0), UserItem.UserOption("미국", 0), UserItem.UserOption("캐나다", 0), UserItem.UserOption("영국", 0), UserItem.UserOption("싱가포르", 0), UserItem.UserOption("대만", 0), UserItem.UserOption("기타", 0))
    }

    fun salary() {
        userOptionList = arrayListOf(UserItem.UserOption("2000만원 미만", 0), UserItem.UserOption("3000만원 이상", 0), UserItem.UserOption("4000만원 이상", 0), UserItem.UserOption("5000만원 이상", 0), UserItem.UserOption("6000만원 이상", 0), UserItem.UserOption("7000만원 이상", 0), UserItem.UserOption("8000만원 이상", 0))
    }


}