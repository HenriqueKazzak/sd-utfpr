# Arquitetura

O `srv-producer-a` é um endpoint que recebe uma requisição POST com um JSON no corpo da requisição. O JSON é enviado para um endpoint no serviço `external-service` que ao receber a solicitação posta no tópico "onboarding-queue" do Rabbit. 
<br>
O `srv-consumer-onboarding` consome desse tópico `onboarding-queue` e faria a persistência no banco de dados, porém no momento ele apenas loga a mensagem recebida.
<br>
No `srv-produver-a` existe um Circuit Breaker que é acionado caso o serviço `external-service` esteja fora do ar. Os parâmetros são:<br>
```yaml
    register-health-indicator: true
    failureRateThreshold: 40
    minimumNumberOfCalls: 10
    slowCallDurationThreshold: 1s
    slowCallRateThreshold : 40
    slidingWindowSize: 10
    slidingWindowType: count_based
    waitDurationInOpenState: 1m
    maxWaitDurationInHalfOpenState: 5m
    permittedNumberOfCallsInHalfOpenState: 10
    automaticTransitionFromOpenToHalfOpenEnabled: true
```
Quando o CB muda seu estado para open, o `srv-producer-a` posta uma mensagem no tópico `cb-open-queue` com as métricas do CB no momento em que foi aberto.
<br>
O `cb-consumer` consome desse tópico `cb-open-queue` e enviaria um e-mail com as métricas do CB para o time tomar alguma providência, porém no momento ele apenas loga a mensagem recebida.