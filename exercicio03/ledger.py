import uuid
from erros import SaldoInsuficienteError


class Ledger:

    def __init__(self):
        self.historico = []

    def saldo(self):
        saldo = 0

        for transacao in self.historico:

            if transacao["status"] == "CONCLUIDO":

                if transacao["tipo"] == "CREDITO":
                    saldo += transacao["valor"]
                else:
                    saldo -= transacao["valor"]

        return saldo

    def depositar(self, valor):

        transacao = {
            "id": str(uuid.uuid4()),
            "valor": valor,
            "tipo": "CREDITO",
            "status": "CONCLUIDO"
        }

        self.historico.append(transacao)

        return transacao

    def sacar(self, valor):

        if self.saldo() - valor < 0:
            raise SaldoInsuficienteError("Saldo insuficiente")

        transacao = {
            "id": str(uuid.uuid4()),
            "valor": valor,
            "tipo": "DEBITO",
            "status": "CONCLUIDO"
        }

        self.historico.append(transacao)

        return transacao

    def estornar(self, id_transacao):

        for transacao in self.historico:

            if transacao["id"] == id_transacao:

                if transacao["status"] == "CONCLUIDO":
                    transacao["status"] = "ESTORNADO"

                return