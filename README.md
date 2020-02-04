# 스프링의 장점

## 1. 느슨한 결합
- 느슨한 결합이란 interface와 DI를 적절히 활용하여 객체간의 결합도를 낮추는 것.

- <a href="https://taesan94.tistory.com/85?category=364478">[ 정리블로그 링크 ]</a>

## 2. DI ( Dependency Injection )
- 의존관계 CLASS들의 객체 생성을 직접하지않고( new연산자 사용하지않고 ), 외부에서 대신 생성해줌으로서 코드변경 사항을 효율적으로 감소시킬 수 있다.

- <a href="https://taesan94.tistory.com/86?category=364478">[ 정리블로그 링크 ]</a>

## 3. IoC ( Inversion of Control )
- 기존에 자바 어플리케이션을 개발할 때, 자바 객체를 생성하고 서로간의 의존관계를 연결시키는 작업에 대한 제어권은 보통 개발되는 어플리케이션에 있었다.

- 그러나 Servlet, EJB등을 사용하는 경우 Servlet Container, EJB Container에게 제어권이 넘어가서 객체의 생명주기(라이프사이클)을 Container들이 전담하게 된다.

- 이처럼 IoC에서 이야기하는 제어권의 역전이란 객체의 생성에서부터 생명주기의 관리까지 모든 객체에 대한 제어권이 바뀌었다는 것을 의미한다.

- Spring의 IoC컨테이너는 일반적으로 ApplicationContext 인터페이스를 구현 한 클래스를 뜻한다.

- <a href="https://taesan94.tistory.com/86?category=364478">[ 정리블로그 링크 ]</a>

## 4. AOP ( Aspect Oriented Programming )

- AOP를 활용하면 비즈니스로직과 이외의 로직을 분리할 수 있다.

- 이외의 로직은 보안,트랜잭션,로깅등의 작업이 있다.

- 이외 로직들을 분리하는 것이 AOP의 방법론이다.

- Proxy라는 도구를 사용해서 이외 로직들을 수행하면서 메인 프로세스를 호출하도록 제어할 수 있다.

- <a href="https://taesan94.tistory.com/89?category=364478">[ 정리블로그 링크 ]</a>

## 5. 스프링 활용하기

- SpringSecurity사용해보기. <a href="">[ Detail ]</a>

## 삽질이력

<table>
  <tr>
    <td>이슈 내용</td>
    <td>조치 내용</td>
  </tr>
  <tr>
    <td>servlet클래스에서 String을 return하여 [ .jsp ] 페이지로 forwarding 기대하였으나, 화면에 출력됨..</td>
    <td><a href="https://github.com/Taesan94/Spring/issues/1"> [  ]</a></td>
  </tr>
<table>
