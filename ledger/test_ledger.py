import pytest

from ledger import Ledger
from erros import SaldoInsuficienteError


def test_deposito():
    conta = Ledger()

    conta.depositar(100)

    assert conta.saldo() == 100


def test_saque():
    conta = Ledger()

    conta.depositar(100)
    conta.sacar(40)

    assert conta.saldo() == 60


def test_saque_sem_saldo():
    conta = Ledger()

    conta.depositar(100)

    with pytest.raises(SaldoInsuficienteError):
        conta.sacar(150)


def test_historico():
    conta = Ledger()

    conta.depositar(100)
    conta.sacar(40)

    assert len(conta.historico) == 2


def test_estorno():
    conta = Ledger()

    transacao = conta.depositar(100)

    conta.estornar(transacao["id"])

    assert conta.saldo() == 0
    assert transacao["status"] == "ESTORNADO"


def test_estorno_saque():
    conta = Ledger()

    conta.depositar(100)
    transacao = conta.sacar(40)

    conta.estornar(transacao["id"])

    assert conta.saldo() == 100
    assert transacao["status"] == "ESTORNADO"


def test_sequencia_de_operacoes():
    conta = Ledger()

    deposito1 = conta.depositar(100)
    conta.depositar(50)
    conta.sacar(30)

    conta.estornar(deposito1["id"])

    assert conta.saldo() == 20
    assert len(conta.historico) == 3
