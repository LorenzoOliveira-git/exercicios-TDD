from erros import ChavePixInvalidaError
from validate_docbr import CPF
import re
import uuid


def valida_chave_pix(chave, tipo):

    if tipo.upper() == "CPF":

        if len(chave) != 11 or not chave.isdigit():
            raise ChavePixInvalidaError(
                "O CPF deve ter 11 dígitos numéricos"
            )

        cpf = CPF()

        if not cpf.validate(chave):
            raise ChavePixInvalidaError(
                "O CPF deve ser válido"
            )

    elif tipo.upper() == "EMAIL":

        padrao = r"^[\w.-]+@[\w.-]+\.[a-zA-Z]{2,}$"

        if not re.match(padrao, chave):
            raise ChavePixInvalidaError(
                "Email inválido"
            )

    elif tipo.upper() == "TELEFONE":

        padrao = r"^\+55\d{11}$"

        if not re.match(padrao, chave):
            raise ChavePixInvalidaError(
                "Telefone inválido"
            )

    elif tipo.upper() == "EVP":

        try:
            chave_uuid = uuid.UUID(chave)

            if chave_uuid.version != 4:
                raise ChavePixInvalidaError(
                    "A chave EVP deve ser um UUIDv4"
                )

        except ValueError:
            raise ChavePixInvalidaError(
                "A chave EVP deve ser um UUIDv4 válido"
            )

    else:
        raise ChavePixInvalidaError(
            "Tipo de chave Pix inválido"
        )