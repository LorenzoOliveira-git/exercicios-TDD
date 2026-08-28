import pytest
from validador import valida_chave_pix
from erros import ChavePixInvalidaError


def test_cpf_com_tamanho_incorreto():
    with pytest.raises(ChavePixInvalidaError):
        valida_chave_pix("123", "CPF")


def test_cpf_invalido():
    with pytest.raises(ChavePixInvalidaError):
        valida_chave_pix("12345678901", "CPF")


def test_email_invalido():
    with pytest.raises(ChavePixInvalidaError):
        valida_chave_pix("email-invalido", "EMAIL")


def test_telefone_invalido():
    with pytest.raises(ChavePixInvalidaError):
        valida_chave_pix("11999999999", "TELEFONE")


def test_evp_invalido():
    with pytest.raises(ChavePixInvalidaError):
        valida_chave_pix("123456", "EVP")