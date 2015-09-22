/**
 * home.js 
 * 
 * DOM (Document Object Model 스프링을 객체로 해서 처리하는 모델) Tree
 * 
 * Selector 표현식: DOM에서 객체(Element, tag)를 찾는 표현식
 * 1. 태그명으로 찾는 방법 <button></button> 를 찾으려면 button으로 찾으면 됨. 이게 표현식임
 * 2. 클래스명으로 찾는 방법 <button class="btn"></button> btn이라는 클래스를 가지고 있는 객체를 찾는 표현식은 .btn 임
 * 3. ID로 찾는 방법 <button id="slideDown"></button> id이라는 아이디를 가지고 있는 객체를 찾는 표현식은 #slideDown 임
 *   
 * jQuery 합수: jQuery()
 * jQuery()가 리턴하는 Type을 => jQueryWrapper 객체 
 * 						  => jQuery 객체
 * 						  => jQuery 집합 객체
 * $() == jQuery() 임. 둘이 똑같은 애들임. 간략하게 쓰기위함.    
 */

function xxx(event) {
//	alert("button click..."); 
	console.log("button click...event"+event);
//	$('img').slideToggle(1000);
	$('img').fadeToggle(1000);
}

$('span').click(xxx).draggable();//메소드 체인방식. 이벤트 연이어 발생. 클릭하고 드래그
//$('button').draggable();
$('img').draggable();