
# DirectCheckout Android

SDK para criptografia e validação de dados de cartão de crédito para integração com a API de pagamentos da Juno/BoletoBancário.

Visando garantir a segurança das transações realizadas em nossa plataforma, a API da Juno adota uma política de criptografia dos dados de cartão de crédito de ponta-a-ponta.

Para mais informações acesse nossa página de integração:

[Integração via API](https://www.boletobancario.com/boletofacil/integration/integration.html) 

## Setup

Adicione o SDK nas dependências do seu aplicativo

```gradle
dependencies {
    implementation "br.com.juno:directcheckout:0.0.3"
}
```

No arquivo Manifest adicione a permissão de acesso à internet, e seu token público que pode ser obtido em nossa [página de integração](https://www.boletobancario.com/boletofacil/integration/integration.html) 

```xml
<manifest>

    <uses-permission android:name="android.permission.INTERNET"/>

    <application
           ...
           android:name=".MyApplication"
           ...>

        <meta-data
                android:name="br.com.juno.directcheckout.public_token"
                android:value="YOUR_PUBLIC_TOKEN"/>

            ...

    </application>

</manifest>
```

Na sua classe application inicialize o SDK:
```kotlin
class MyApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        DirectCheckout.initialize(this)
    }
}
```

Para utilizar o ambiente de testes (Sandbox) basta passar false no segundo parâmetro (prod):

```kotlin
class MyApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        DirectCheckout.initialize(this, prod = false)
    }
}
```


## Utilização


Detalhamos a seguir um exemplo de utilização de nossa biblioteca de como obter o hash do cartão de crédito:

```kotlin
  val card = Card(
      cardNumber = "999999999",
      holderName = "Teste",
      securityCode = "111",
      expirationMonth = "6",
      expirationYear = "2022"
  )

  DirectCheckout.getCardHash(card, object : DirectCheckoutListener<String> {
      
      override fun onSuccess(cardHash: String) {
          /* Sucesso - A variável cardHash conterá o hash do cartão de crédito */
      }

      override fun onFailure(exception: DirectCheckoutException) {
          /* Erro - A variável exception conterá o erro ocorrido ao obter o hash */
      }
  })
```

Caso esteja utilizando a linguagem Java:

```java
  Card card  = new Card(
      "9999 9999 9999 9999",
      "Teste",
      "111",
      "6",
      "2022"
  );


  DirectCheckout.getCardHash(card, new DirectCheckoutListener<String>() {
      @Override
      public void onSuccess(@NotNull String cardHash) {
          /* Sucesso - A variável cardHash conterá o hash do cartão de crédito */
      }

      @Override
      public void onFailure(@NotNull DirectCheckoutException exception) {
          /* Erro - A variável exception conterá o erro ocorrido ao obter o hash */
      }
  });
```


## Funções Auxiliares

A biblioteca disponibilizada também possui uma série de métodos auxiliares para a validação de dados do cartão de crédito, conforme demonstrado a seguir:

```kotlin
  /* isValidSecurityCode: Valida número do cartão de crédito (retorna true se for válido) */
  DirectCheckout.isValidCardNumber("9999999999999999")

  /* isValidSecurityCode: Valida código de segurança do cartão de crédito (retorna true se for válido) */
  DirectCheckout.isValidSecurityCode("9999999999999999", "111")

  /* isValidExpireDate: Valida data de expiração do cartão de crédito (retorna true se for válido) */
  DirectCheckout.isValidExpireDate("05", "2021")

  /* isValidCardData: Validação dos dados do cartão de crédito(retorna true se for válido) */
  DirectCheckout.isValidCardData(card)

  /* getCardType: Obtém o tipo de cartão de crédito (bandeira) */
  DirectCheckout.getCardType("9999999999999999")}

```

Algumas funções também podem ser acessadas diretamente da classe Card:

```kotlin

  val card = Card(
    cardNumber = "999999999",
    holderName = "Teste",
    securityCode = "111",
    expirationMonth = "6",
    expirationYear = "2022"
  )

  /* isValidSecurityCode: Valida número do cartão de crédito (retorna true se for válido) */
  card.validateNumber()

  /* isValidSecurityCode: Valida código de segurança do cartão de crédito (retorna true se for válido) */
  card.validateCVC()

  /* isValidExpireDate: Valida data de expiração do cartão de crédito (retorna true se for válido) */
  card.validateExpireDate()

  /* getCardType: Obtém o tipo de cartão de crédito (bandeira) */
  card.getType()

```

## Contato 

Para mais informações entre em contato com a Juno:

* https://juno.com.br/contato.html


