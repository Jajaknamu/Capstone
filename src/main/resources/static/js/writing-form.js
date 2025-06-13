
// first combo box - 단과대 선택
data_1 = new Option("선택1", "선택1");
data_2 = new Option("선택2", "선택2");
data_3 = new Option("선택3", "선택3");
data_4 = new Option("선택4", "선택4");


// second combo box - 학과 선택
// 인문대 학과_옵션
data_1_1 = new Option("사회복지학과", "사회복지학과");
data_1_2 = new Option("유아교육학과", "유아교육학과");
data_1_3 = new Option("법경찰학과", "법경찰학과");
data_1_4 = new Option("경영학과", "경영학과");

// 공대 학과_옵션
data_2_1 = new Option("컴퓨터공학과", "컴퓨터공학과");
data_2_2 = new Option("전기전자학과", "전기전자학과");
data_2_3 = new Option("건축학과", "건축학과");
data_2_4 = new Option("화학공학과", "화학공학과");

// 예체능대 학과_옵션
data_3_1 = new Option("공연예술학과", "공연예술학과");
data_3_2 = new Option("디자인학과", "디자인학과");
data_3_3 = new Option("사회체육학과", "사회체육학과");

// 보건대 학과_옵션
data_4_1 = new Option("간호학과", "간호학과");
data_4_2 = new Option("임상병리학과", "임상병리학과");
data_4_3 = new Option("물리치료학과", "물리치료학과");
data_4_4 = new Option("제약학과", "제약학과");

// third combo box - 교수님 이름
// 인문대-사회복지학과 교수님
data_1_1_1 = new Option("김교수", "김교수");
data_1_1_2 = new Option("이교수", "이교수");

// 인문대-유아교육학과 교수님
data_1_2_1 = new Option("김교수", "김교수");
data_1_2_2 = new Option("이교수", "이교수");

// 인문대-법경찰학과 교수님
data_1_3_1 = new Option("김교수", "김교수");
data_1_3_2 = new Option("이교수", "이교수");

// 인문대-경영학과 교수님
data_1_4_1 = new Option("김교수", "김교수");
data_1_4_2 = new Option("이교수", "이교수");

// 선택2_옵션1_옵션
data_2_1_1 = new Option("선택2_옵션1_옵션1", "선택2_옵션1_옵션1");
data_2_1_2 = new Option("선택2_옵션1_옵션2", "선택2_옵션1_옵션2");

// 선택2_옵션2_옵션
data_2_2_1 = new Option("선택2_옵션2_옵션1", "선택2_옵션2_옵션1");
data_2_2_2 = new Option("선택2_옵션2_옵션2", "선택2_옵션2_옵션2");

// 선택3_옵션1_옵션
data_3_1_1 = new Option("선택3_옵션1_옵션1", "선택3_옵션1_옵션1");
data_3_1_2 = new Option("선택3_옵션1_옵션2", "선택3_옵션1_옵션2");

// 선택3_옵션2_옵션
data_3_2_1 = new Option("선택3_옵션2_옵션1", "선택3_옵션2_옵션1");
data_3_2_2 = new Option("선택3_옵션2_옵션2", "선택3_옵션2_옵션2");

// other parameters
displaywhenempty="-선택없음-"
valuewhenempty=""
displaywhennotempty="- 항목선택↓  -"
valuewhennotempty=""

function change(currentbox) {
    numb = currentbox.id.split("_");
    currentbox = numb[1];
    i=parseInt(currentbox)+1
//son = xxx와 같다
// 현재 콤보 상자 뒤에 있는 모든 콤보 상자를 비웁니다
    while ((eval("typeof(document.getElementById(\"combo_"+i+"\"))!='undefined'")) &&
    (document.getElementById("combo_"+i)!=null)) {
        son = document.getElementById("combo_"+i);
        // 첫 번째 옵션을 제외한 모든 옵션을 비웁니다(허용되지 않음)
        for (m=son.options.length-1;m>0;m--) son.options[m]=null;
        // 첫 번째 옵션을 재설정했습니다
        son.options[0]=new Option(displaywhenempty,valuewhenempty)
        i=i+1
    }

// 이제 "기본" 이름("stringa")으로 문자열을 만듭니다. 즉, "data_1_0"
// 여기에 _0,_1,_2,_3 등을 추가하여 채울 콤보 상자의 이름을 얻습니다
    stringa='data'
    i=0
    while ((eval("typeof(document.getElementById(\"combo_"+i+"\"))!='undefined'")) &&
    (document.getElementById("combo_"+i)!=null)) {
        eval("stringa=stringa+'_'+document.getElementById(\"combo_"+i+"\").selectedIndex")
        if (i==currentbox) break;
        i=i+1
    }

// 'xxx(=son)' 콤보 채우기(존재하는 경우)
    following=parseInt(currentbox)+1
    if ((eval("typeof(document.getElementById(\"combo_"+following+"\"))!='undefined'")) &&
        (document.getElementById("combo_"+following)!=null)) {
        son = document.getElementById("combo_"+following);
        stringa=stringa+"_"
        i=0
        while ((eval("typeof("+stringa+i+")!='undefined'")) || (i==0)) {
            // 옵션이 없으면 "xxx(=son)" 콤보의 첫 번째 옵션을 비웁니다. 그렇지 않으면 "-select-"를 입력합니다
            if ((i==0) && eval("typeof("+stringa+"0)=='undefined'"))
                if (eval("typeof("+stringa+"1)=='undefined'"))
                    eval("son.options[0]=new Option(displaywhenempty,valuewhenempty)")
                else
                    eval("son.options[0]=new Option(displaywhennotempty,valuewhennotempty)")
            else
                eval("son.options["+i+"]=new Option("+stringa+i+".text,"+stringa+i+".value)")
            i=i+1
        }
        //xxx(=son).focus()
        i=1
        combostatus=''
        cstatus=stringa.split("_")
        while (cstatus[i]!=null) {
            combostatus=combostatus+cstatus[i]
            i=i+1
        }
        return combostatus;
    }
}
