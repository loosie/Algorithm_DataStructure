=begin

   루비에서는 독립적인 프로세스를 생성하거나(spawn) 관리할 수 있는 여러 가지 방법을 제공한다.

   새로운 프로세스 생성
   1) 가장 쉬운 방법은 명령을 실행하고 끝나기를 기다리는 것이다.
      Object#system 메서드는 주어진 명령을 하위 프로세스에서 실행한다. 프로세스가 실패했을 때 서브 프로세스의 종료 코드는 전역 변수 $?를 통해 확인할 수 있다.

   그러나 대부분의 경우 훨씬 많은 제어가 필요하다. 하위 프로세스와 대화를 하고 데이터를 주고받아야 하는 경우도 있다. IO.popen이 이러한 일을 한다.
   2) IO.popen은 하위 프로세스로 명령을 실행하고, 하위 프로세스의 표준 입력과 표준 출력을 루비 코드에서 사용할 수 있게 해준다.
=end

# IO.popen은 File.open과 매우 비슷한 방식으로 블록을 활용한다. date 같은 명령과 함께 블록을 건네주면, 블록은 IO 객체를 인자로 해서 실행된다.
IO.popen("date") { |f| puts "date is #{f.gets}" }

#  IO 객체는 File.open이 그러하듯 코드 블록이 종료될 때 자동으로 닫힌다. fork와 블록을 결합하면, 블록 안의 루비 코드는 루비 하위 프로세스에서 실행되고, 부모는 블록 이후에 수행된다.
fork do
  puts "In child, pid = #$$"
  exit 99
end

pid = Process.wait
puts "Child terminated, pid = #{pid}, status = #{$?.exitstatus}"

# $?는 하위 프로세스의 종료에 대한 정보를 가진 전역변수다. 이 변수는 Process.wait를 호출할 때마다 갱신된다.

